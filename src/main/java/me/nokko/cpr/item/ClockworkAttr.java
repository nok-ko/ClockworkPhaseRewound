package me.nokko.cpr.item;

// Javadocs ripped from Lumaceon's original Clockwork Phase, and reformatted.
public enum ClockworkAttr {
    /**
     * Gear speed, along with quality, is used different ways depending on the item.
     * <p>
     * Typically, a higher gear speed will exhaust the mainspring's tension at a
     * faster rate, but will offset this with a higher power.
     * <p>
     * A general guide to gear speed with common metals:
     * <ul>
     *     <li> 0 &mdash; You built a gear out of dirt and it flops around like a whale on a small island. </li>
     *     <li> 5 &mdash; Gold gear. </li>
     *     <li> 10 &mdash; Lead gear. </li>
     *     <li> 20 &mdash; Steel gear. </li>
     *     <li> 25 &mdash; Iron/bronze gears. </li>
     *     <li> 30 &mdash; Tin gear. </li>
     *     <li> 35 &mdash; Silver gear. </li>
     *     <li> 40 &mdash; Copper gear. </li>
     *     <li> 40 &mdash; Diamond gear. </li>
     *     <li> 45 &mdash; Brass gear. </li>
     *     <li> 60 &mdash; Emerald gear. </li>
     * </ul>
     */
    SPEED,

    /**
     * What gear quality determines is dependant on the construct using it.
     * <p>
     * In most cases, quality will determine how efficient the tool is with
     * its tension, using significantly less if quality is higher then speed.
     */
    QUALITY,

    /**
     * Memory value represents the total memories that this item holds. In many cases, this
     * value may return the result of an NBT value held by the component itself. Memory effects
     * the amount of memories.
     * <p>
     * Memory typically adds a "secondary benefit" to the resultant item.
     */
    MEMORY
}
