package Data.Interfaces;

import Data.DataFrame;
import Data.Values.exceptions.InvalidData;

public interface Groubby {
    DataFrame max()throws InvalidData;
    DataFrame min()throws InvalidData;
    DataFrame mean()throws InvalidData;
    DataFrame std()throws InvalidData;
    DataFrame sum()throws InvalidData;
    DataFrame var()throws InvalidData;
    DataFrame apply(Applyable a)throws InvalidData;
}
