package Data.Interfaces;

import Data.DataFrame;
import Data.Values.Value;
import Data.exceptions.InvalidData;
import Data.exceptions.InvalidOperation;

import java.util.ArrayList;

public interface Applyable {
    DataFrame apply(DataFrame object)throws InvalidData;
    void operate(DataFrame df,ArrayList<Value> temp, int b)throws InvalidData, InvalidOperation;
}
