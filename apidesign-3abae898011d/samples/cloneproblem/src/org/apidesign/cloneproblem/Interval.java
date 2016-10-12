package org.apidesign.cloneproblem;

import java.util.Date;

// BEGIN: interval.api
/** Quiz: Anyone can come up with a JUnit test to generate
 * {@link NullPointerException} directly from the code of 
 * the <code>Interval</code> class?
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
public final class Interval {
    private final Date from, to;
    
    /** Constructs interval between two dates.
     * 
     * @param from the 'sooner' date
     * @param to the 'later' date
     * @throws IllegalArgumentException 
     *      if <code>from</code> is not less then <code>to</code>
     */
    public Interval(Date from, Date to) {
        if (from == null) {
            throw new IllegalArgumentException("'from' cannot be null!");
        }
        if (to == null) {
            throw new IllegalArgumentException("'to' cannot be null!");
        }
        // shield us from Date's mutability
        this.from = (Date) from.clone();
        this.to = (Date)to.clone();
        if (from.compareTo(to) >= 0) {
            throw new IllegalArgumentException(
                "'from' must be lower than 'to'!"
            );
        }
    }
    
    /** The length of the interval in milliseconds 
     * 
     * @return amount of milliseconds between 'from' and 'to' dates.
     */
    public long getLength() {
        return to.getTime() - from.getTime();
    }
}
// END: interval.api
