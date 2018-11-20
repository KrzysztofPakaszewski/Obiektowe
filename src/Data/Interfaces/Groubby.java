package Data.Interfaces;

import Data.DataFrame;
import Data.exceptions.CannotCreateValueFromString;
import Data.exceptions.Error;
import Data.exceptions.InvalidData;

public interface Groubby {
    DataFrame max()throws InvalidData, Error, CannotCreateValueFromString;
    DataFrame min()throws InvalidData, Error, CannotCreateValueFromString;
    DataFrame mean()throws InvalidData, Error, CannotCreateValueFromString;
    DataFrame std()throws InvalidData, Error, CannotCreateValueFromString;
    DataFrame sum()throws InvalidData, Error, CannotCreateValueFromString;
    DataFrame var()throws InvalidData, Error, CannotCreateValueFromString;
    DataFrame apply(Applyable a)throws InvalidData, Error, CannotCreateValueFromString;
}
