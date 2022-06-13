/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ohjelmistotuotanto;

import com.spire.doc.Document;
import com.spire.doc.Table;
import com.spire.doc.fields.Field;

public class LaskuLuokka {
    public static void addRows(Table table, int rowNum) {
        for (int i = 0; i < rowNum; i++) {
            //insert specific number of rows by cloning the second row
            table.getRows().insert(2 + i, table.getRows().get(1).deepClone());
            //update formulas for Total
            for (Object object : table.getRows().get(2 + i).getCells().get(3).getParagraphs().get(0).getChildObjects()
            ) {
                if (object instanceof Field) {
                    Field field = (Field) object;
                    field.setCode(String.format("=B%d*C%d\\# \"0.00\"", 3 + i,3 + i));
                }
                break;
            }
        }
        //update formula for Total Tax
        for (Object object : table.getRows().get(4 + rowNum).getCells().get(3).getParagraphs().get(0).getChildObjects()
        ) {
            if (object instanceof Field) {
                Field field = (Field) object;
                field.setCode(String.format("=D%d*0.24\\# \"0.00\"", 3 + rowNum));
            }
            break;
        }
        //update formula for Balance Due
        for (Object object : table.getRows().get(5 + rowNum).getCells().get(3).getParagraphs().get(0).getChildObjects()
        ) {
            if (object instanceof Field) {
                Field field = (Field) object;
                field.setCode(String.format("=D%d+D%d\\# \"$#,##0.00\"", 3 + rowNum, 5 + rowNum));
            }
            break;
        }
    }

    public static void fillTableWithData(Table table, String[][] data) {
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < data[r].length; c++) {
                //fill data in cells
                table.getRows().get(r + 1).getCells().get(c).getParagraphs().get(0).setText(data[r][c]);
            }
        }
    }

    public static void writeDataToDocument(Document doc, String[][] purchaseData) {
        //get the third table
        Table table = doc.getSections().get(0).getTables().get(2);
        //determine if it needs to add rows
        if (purchaseData.length > 1) {
            //add rows
            addRows(table, purchaseData.length - 1);
        }
        //fill the table cells with value
        fillTableWithData(table, purchaseData);
    }
}
