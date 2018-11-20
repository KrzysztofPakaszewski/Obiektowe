package Data.Interfaces;

import Data.DataFrame;
import Data.Values.Value;
import Data.exceptions.InvalidOperation;

public interface DataFrameColumnOperations {
    void apply(DataFrame object, int row, int col, Value val)throws InvalidOperation;
}
