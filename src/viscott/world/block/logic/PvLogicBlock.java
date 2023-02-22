package viscott.world.block.logic;

import arc.func.Cons;
import arc.func.Prov;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.core.UI;
import mindustry.core.World;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.logic.LAssembler;
import mindustry.logic.LExecutor;
import mindustry.logic.LStatement;
import mindustry.logic.LStatements;
import mindustry.ui.Styles;
import mindustry.world.blocks.logic.LogicBlock;
import viscott.types.logic.PvAssembler;
import viscott.world.ui.PvUI;

import static mindustry.Vars.world;
import static viscott.content.PvUIs.*;

public class PvLogicBlock extends LogicBlock {
    public Seq<Prov<LStatement>> allStatements;
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
                    asm.putConst("@ipt", instructionsPerTick);

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
    }
}
