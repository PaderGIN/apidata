package ru.glebpad.apidata;

import joinery.DataFrame;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        DataFrame df = DataFrame.readCsv("../../../Developer/data_libra.csv",";","22",true);
        df.retain("ID","ContractDate", "ContractReestrNumber", "ContractSubject","ContractSumm","GovernmentCustomerINN","PayerINN","INNSupplier");


        df.writeCsv("../../../Developer/data_libra_Contract.csv");
        System.out.println(df);
    }
}