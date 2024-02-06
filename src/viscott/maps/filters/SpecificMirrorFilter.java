package viscott.maps.filters;


import mindustry.Vars;
import mindustry.maps.filters.*;
import mindustry.world.Block;

public class SpecificMirrorFilter extends MirrorFilter {
    Block b;
    @Override
    public FilterOption[] options(){
        return new FilterOption[]{
                new PvFilterOption.SliderOption("angle", () -> angle, f -> angle = (int)f, 0, 360, 15),
                new PvFilterOption.ToggleOption("rotate", () -> rotate, f -> rotate = f),
                new PvFilterOption.BlockOption("only",()-> b,v -> b = v,(i)->!i.destructible)
        };
    }
}
