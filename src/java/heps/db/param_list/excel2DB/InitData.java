/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.excel2DB;

import heps.db.param_list.db.ejb.DivisionFacade;
import heps.db.param_list.db.ejb.SubdivisionFacade;
import heps.db.param_list.db.ejb.SystemFacade;
import heps.db.param_list.db.entity.Division;
import heps.db.param_list.db.entity.Subdivision;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author lvhuihui
 */
public class InitData {
 
    public InitData() {
       
    }

    public void data2DB(File file) {
        Workbook wb = ExcelTool.getWorkbook(file);
        ReadExcel re = new ReadExcel(wb);
        ArrayList dataList = re.getSheet();
       // System.out.println(dataList);
        
        String div = null;
        String subdiv = null;
        String sys = null;
        
        Division division=new Division();
        Subdivision subdivision=new Subdivision();
        heps.db.param_list.db.entity.System system=new heps.db.param_list.db.entity.System();
        
        DivisionFacade divFacade = new DivisionFacade();
        System.out.println(divFacade);
        SubdivisionFacade subFacade = new SubdivisionFacade();
        SystemFacade sysFacade = new SystemFacade();
        
        Iterator it = dataList.iterator();
        System.out.println(dataList);
        while (it.hasNext()) {
            ArrayList oneRow = (ArrayList) it.next();
            System.out.println(oneRow);
            if (!oneRow.get(0).toString().toLowerCase().equals("division") && !"总体".equals(oneRow.get(0).toString()) && !oneRow.get(0).equals("")) {
                div = (String) oneRow.get(0);
                System.out.println(div);
                subdiv = (String) oneRow.get(1);
                sys = (String) oneRow.get(2);
                
                division=divFacade.getDivision(div);
                if(division==null) division=divFacade.setDivision(div);
                System.out.println(division);
                
                subdivision=subFacade.getSubdivision(division, subdiv);
                if(subdivision==null) subdivision=subFacade.setSubdivision(division, subdiv);
                
                system=sysFacade.getSystem(subdivision, sys);
                if(system==null) system=sysFacade.setSystem(subdivision, sys);
            }
       
        }

    }
    
    public void initEmptySubdivision(){
       DivisionFacade divisionFacade=new DivisionFacade();
       List<Division> divList=divisionFacade.getAllDivision();
       Iterator it=divList.iterator();
       while(it.hasNext()){
          Division d=(Division) it.next();
          Subdivision sub=new SubdivisionFacade().setSubdivision(d, "0");
          new SystemFacade().setSystem(sub, "0");
       }
    }
    
    public void initACdata(){
        
    }

  /*  public void division2DB() {
         Workbook wb = ExcelTool.getWorkbook(file);
        ReadExcel re = new ReadExcel(wb);
        ArrayList dataList = re.getSheet();
        int col = new ListTool(dataList).getColNum("division");
        ArrayList divData = new ArrayList();
        Iterator it = dataList.iterator();
        while (it.hasNext()) {
            ArrayList a = (ArrayList) it.next();
            divData.add(a.get(col));
        }
        Iterator it1 = divData.iterator();
        while (it1.hasNext()) {
            String s = (String) it1.next();
            if (s != null && "" != s) {
                if (!(s.toLowerCase().contains("division") || s.equals("总体"))) {
                    DivisionFacade facade = new DivisionFacade();
                    if (facade.getDivision(s) == null) {
                        facade.setDivision(s);
                    }
                }
            }
        }
    }*/

}
