package viscott.content.pressure;

/** Basic interface for any block that produces pressure.*/
public interface PressureBlock{
    float pressure();
    /** @return heat as a fraction of max pressure */
    float pressureFraction();
}
