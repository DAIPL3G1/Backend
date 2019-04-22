/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.service;

import com.saferus.backend.model.Vehicle;
import com.saferus.backend.model.Bind;
import com.saferus.backend.model.ValidateBind;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author lucasbrito
 */
public interface BindService {
    
    public void requestBind(String plate, String broker_nif, String user_nif) ;
    public Bind validateBind(ValidateBind vb, int bind_id) throws Exception;
    public Bind unvalidateBind(int bind_id) throws Exception;
    public void unbind(String user_nif) throws Exception;
    public List<Bind> readBinds();
    public Bind readBind(int bind_id);
    public Bind updateBind(int bind_id, Bind bind);
    public List<Bind> readAllPendingBind(String broker_nif);
    public void unbindVehicle(int vehicle_id) throws Exception;
    
}
