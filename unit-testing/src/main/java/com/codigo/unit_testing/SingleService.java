package com.codigo.unit_testing;

import com.codigo.unit_testing.aggregates.request.EmpresaRequest;
import com.codigo.unit_testing.aggregates.response.BaseResponse;

public class SingleService {
    private static EmpresaRequest instanceEmpresa;
    private static BaseResponse instanceBaseResponse;

    public static EmpresaRequest getInstance(){
        if(instanceEmpresa == null){
            instanceEmpresa = new EmpresaRequest();
        }
        return instanceEmpresa;
    }

    public static BaseResponse getInstanceBaseResponse(){
        if(instanceBaseResponse == null){
            instanceBaseResponse = new BaseResponse();
        }
        return instanceBaseResponse;
    }

}
