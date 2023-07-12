package viscott.world.block.logic;

import arc.func.Cons;
import arc.func.Prov;
import arc.scene.ui.layout.Table;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.core.UI;
import mindustry.core.World;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.logic.*;
import mindustry.ui.Styles;
import mindustry.world.blocks.logic.LogicBlock;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import viscott.types.PvFaction;
import viscott.types.logic.PvAssembler;
import viscott.world.ui.PvUI;

import static mindustry.Vars.state;
import static mindustry.Vars.world;
import static viscott.content.PvUIs.*;

public class PvLogicBlock extends LogicBlock {

    public Seq<PvFaction> faction = new Seq<>();
    public Seq<Prov<LStatement>> allStatements;
    public DrawBlock drawer = new DrawDefault();
    public PvLogicBlock(String name)
    {
        super(name);
        allStatements= Seq.with(
                new Prov[]{
                        LStatements.InvalidStatement::new,
                        LStatements.ReadStatement::new,
                        LStatements.WriteStatement::new,
                        LStatements.DrawStatement::new,
                        LStatements.PrintStatement::new,
                        LStatements.DrawFlushStatement::new,
                        LStatements.PrintFlushStatement::new,
                        LStatements.GetLinkStatement::new,
                        LStatements.ControlStatement::new,
                        LStatements.RadarStatement::new,
                        LStatements.SensorStatement::new,
                        LStatements.SetStatement::new,
                        LStatements.OperationStatement::new,
                        LStatements.WaitStatement::new,
                        LStatements.StopStatement::new,
                        LStatements.LookupStatement::new,
                        LStatements.PackColorStatement::new,
                        LStatements.EndStatement::new,
                        LStatements.JumpStatement::new,
                        LStatements.UnitBindStatement::new,
                        LStatements.UnitControlStatement::new,
                        LStatements.UnitRadarStatement::new,
                        LStatements.UnitLocateStatement::new,
                        LStatements.GetBlockStatement::new,
                        LStatements.SetBlockStatement::new,
                        LStatements.SpawnUnitStatement::new,
                        LStatements.ApplyStatusStatement::new,
                        LStatements.SpawnWaveStatement::new,
                        LStatements.SetRuleStatement::new,
                        LStatements.FlushMessageStatement::new,
                        LStatements.CutsceneStatement::new,
                        LStatements.ExplosionStatement::new,
                        LStatements.SetRateStatement::new,
                        LStatements.FetchStatement::new,
                        LStatements.GetFlagStatement::new,
                        LStatements.SetFlagStatement::new});
    }

    @Override
    public void init() {
        super.init();
        drawer.load(this);
    }

    public boolean partOfPlayerFaction()
    {
        return faction.size == 0 || faction.count(f -> f.partOf(Vars.player.team())) > 0;
    }

    @Override
    public boolean isVisible(){
        return state.rules.editor || (partOfPlayerFaction() && !isHidden() && (!state.rules.hideBannedBlocks || !state.rules.isBanned(this)));
    }

    @Override
    public boolean isPlaceable(){
        return Vars.net.server() || (!state.rules.isBanned(this) || state.rules.editor) && supportsEnv(state.rules.env);
    }
    public class PvLogicBuild extends LogicBuild
    {
        @Override
        public void buildConfiguration(Table table){
            if(!accessible()){
                //go away
                deselect();
                return;
            }

            table.button(Icon.pencil, Styles.cleari, () -> {
                extraUI.customLogic.show(allStatements,code, executor, privileged, code -> configure(compress(code, relativeConnections())));
            }).size(40);
        }

        @Override
        public float warmup() {
            return 1f;
        }
        @Override
        public void draw() {
            drawer.draw(this);
        }

        @Override
        public void updateTile(){
            //load up code from read()
            if(loadBlock != null){
                loadBlock.run();
                loadBlock = null;
            }

            executor.team = team;

            if(!checkedDuplicates){
                checkedDuplicates = true;
                var removal = new IntSet();
                var removeLinks = new Seq<LogicLink>();
                for(var link : links){
                    var build = world.build(link.x, link.y);
                    if(build != null){
                        if(!removal.add(build.id)){
                            removeLinks.add(link);
                        }
                    }
                }
                links.removeAll(removeLinks);
            }

            //check for previously invalid links to add after configuration
            boolean changed = false, updates = true;

            while(updates){
                updates = false;

                for(int i = 0; i < links.size; i++){
                    LogicLink l = links.get(i);

                    if(!l.active) continue;

                    var cur = world.build(l.x, l.y);

                    boolean valid = validLink(cur);
                    if(l.lastBuild == null) l.lastBuild = cur;
                    if(valid != l.valid || l.lastBuild != cur){
                        l.lastBuild = cur;
                        changed = true;
                        l.valid = valid;
                        if(valid){

                            //this prevents conflicts
                            l.name = "";
                            //finds a new matching name after toggling
                            l.name = findLinkName(cur.block);

                            //remove redundant links
                            links.removeAll(o -> world.build(o.x, o.y) == cur && o != l);

                            //break to prevent concurrent modification
                            updates = true;
                            break;
                        }
                    }
                }
            }

            if(changed){
                updateCode(code, true, null);
            }

            if(state.rules.disableWorldProcessors && privileged) return;

            if(enabled && executor.initialized()){
                accumulator += edelta() * ipt;

                if(accumulator > maxInstructionScale * ipt) accumulator = maxInstructionScale * ipt;
                for(int i = 0; i < (int)accumulator; i++){
                    executor.runOnce();
                    accumulator --;
                }
            }
        }

        @Override
        public void updateCode(String str, boolean keep, Cons<LAssembler> assemble) {
            if (str != null) {
                code = str;

                try {
                    //create assembler to store extra variables
                    PvAssembler asm = PvAssembler.pvAssemble(str, privileged);

                    //store connections
                    for (LogicLink link : links) {
                        if (link.active && (link.valid = validLink(world.build(link.x, link.y)))) {
                            asm.putConst(link.name, world.build(link.x, link.y));
                        }
                    }

                    //store link objects
                    executor.links = new Building[links.count(l -> l.valid && l.active)];
                    executor.linkIds.clear();

                    int index = 0;
                    for (LogicLink link : links) {
                        if (link.active && link.valid) {
                            Building build = world.build(link.x, link.y);
                            executor.links[index++] = build;
                            if (build != null) executor.linkIds.add(build.id);
                        }
                    }

                    asm.putConst("@mapw", world.width());
                    asm.putConst("@maph", world.height());
                    asm.putConst("@links", executor.links.length);
                    asm.putVar("@ipt").value = ipt;;

                    if (keep) {
                        //store any older variables
                        for (LExecutor.Var var : executor.vars) {
                            boolean unit = var.name.equals("@unit");
                            if (!var.constant || unit) {
                                LAssembler.BVar dest = asm.getVar(var.name);
                                if (dest != null && (!dest.constant || unit)) {
                                    dest.value = var.isobj ? var.objval : var.numval;
                                }
                            }
                        }
                    }

                    //inject any extra variables
                    if (assemble != null) {
                        assemble.get(asm);
                    }

                    asm.getVar("@this").value = this;
                    asm.putConst("@thisx", World.conv(x));
                    asm.putConst("@thisy", World.conv(y));

                    executor.load(asm);
                } catch (Exception e) {
                    //handle malformed code and replace it with nothing
                    executor.load(LAssembler.assemble(code = "", privileged));
                }
            }
        }
        @Override
        public void write(Writes write){
            super.write(write);
            write.i(ipt);
        }

        @Override
        public void read(Reads read, byte revision)
        {
            super.read(read,revision);
            ipt = read.i();
        }
    }
}
