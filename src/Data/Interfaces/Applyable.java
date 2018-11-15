package Data.Interfaces;

import Data.DataFrame;
import Data.Values.Value;
import Data.Values.exceptions.InvalidData;
import Data.Values.exceptions.InvalidOperation;

import java.util.ArrayList;

public interface Applyable {
    DataFrame apply(DataFrame object)throws InvalidData;
    void operate(DataFrame df,ArrayList<Value> temp, int b)throws InvalidData, InvalidOperation;
}
