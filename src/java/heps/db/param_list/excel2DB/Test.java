/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.excel2DB;

import heps.db.param_list.comman.tools.EmProvider;
import heps.db.param_list.db.ejb.DivisionFacade;
import heps.db.param_list.db.ejb.SubdivisionFacade;
import heps.db.param_list.db.ejb.SystemFacade;
import heps.db.param_list.db.entity.Subdivision;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lvhuihui
 */
public class Test {

    public static void main(String[] args) {

       
        
        File f = new File("data\\initdata.xlsx");
       
       InitData init = new InitData();
    //   init.data2DB(f);
    
     init.initEmptySubdivision();

    }

}
