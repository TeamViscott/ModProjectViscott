package viscott.world.block.logic;

import arc.func.Prov;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.core.UI;
import mindustry.gen.Icon;
import mindustry.logic.LStatement;
import mindustry.logic.LStatements;
import mindustry.ui.Styles;
import mindustry.world.blocks.logic.LogicBlock;
import viscott.world.ui.PvUI;

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
    }
}
