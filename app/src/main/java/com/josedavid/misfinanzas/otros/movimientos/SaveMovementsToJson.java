package com.josedavid.misfinanzas.otros.movimientos;

import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;
import com.josedavid.misfinanzas.otros.json.SaveObjectToJson;

import java.io.File;
import java.util.ArrayList;

public class SaveMovementsToJson {
    public boolean saveMovementsToJson(File filesDir, Usuario usuario){
        boolean success = false;

        ArrayList<Movimiento> listaMovimientos = usuario.getListaMovimientos();

        String folderPath = "/MisFinanzas/" + usuario.getNombre() + "/movimientos/";
        File folder = new File(filesDir + folderPath);

        SaveObjectToJson saveObjectToJson = new SaveObjectToJson();
        success = saveObjectToJson.saveObjectToJson(filesDir,folder,"movimientos",listaMovimientos);

        return success;
    }
}
