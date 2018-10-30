package Data.Interfaces;

import Data.DataFrame;

public interface Groubby {
    DataFrame max();
    DataFrame min();
    DataFrame mean();
    DataFrame std();
    DataFrame sum();
    DataFrame var();
    DataFrame apply(Applyable a);
}
