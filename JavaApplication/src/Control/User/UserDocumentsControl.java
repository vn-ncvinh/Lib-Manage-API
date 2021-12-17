/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.User;

import Model.API;
import Model.List.ListCategory;
import Model.List.ListDocuments;
import Model.User;
import Model.jsonobj;
import Model.url;
import Control.str;
import Model.Documents;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ncvinh
 */
public class UserDocumentsControl {

    static jsonobj result;

    public static void send(url u) {
        System.out.println(u.getUrl());
        API api = new API(u);
        try {
            result = new jsonobj(api.send());
        } catch (IOException ex) {
            Logger.getLogger(UserDocumentsControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(UserDocumentsControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(result.getmessage());
    }

    public static void getList() throws ParseException {
        System.out.println(User.StudentID + ": Get Documents");
        url u = new url("/api/documents/all");
        send(u);
        System.out.println(result.getstatus());
        ListDocuments.clean();
        if (result.getstatus().equals("OK")) {
            for (int i = 0; i < result.lengthdata(); i++) {
//                ID, Name, Author, Description, Available
                ListDocuments.add(result.getdata(i, "ID"), result.getdata(i, "Name"), result.getdata(i, "Author"), result.getdata(i, "Description"), result.getdata(i, "Available"));
            }
        }
    }

    

    public static void search(String key) throws ParseException {
        getList();
        key = str.deAccent(key);
        System.out.println(User.StudentID + ": Search " + key);
        ArrayList<Documents> temp = ListDocuments.list;
        ArrayList<Documents> temp2 = new ArrayList<Documents>();
        ListDocuments.clean();
        for (Documents x : temp) {
            if (!temp2.contains(x) && str.deAccent(x.getAuthor()).contains(key)) {
                temp2.add(x);
            }
        }

        for (Documents x : temp) {
            if (!temp2.contains(x) && str.deAccent(x.getName()).contains(key)) {
                temp2.add(x);
            }
        }

        for (Documents x : temp) {
            if (!temp2.contains(x) && str.deAccent(x.getDescription()).contains(key)) {
                temp2.add(x);
            }
        }
        ListDocuments.list = temp2;
    }

    public static void category() throws ParseException {
        System.out.println(User.StudentID + ": Get Category");
        url u = new url("'/api/documents/category");
        send(u);
        System.out.println(result.getstatus());
        ListDocuments.clean();
        if (result.getstatus().equals("OK")) {
            for (int i = 0; i < result.lengthdata(); i++) {
                ListCategory.add(result.getdata(i, "ID"), result.getdata(i, "Name"), result.getdata(i, "Description"), result.getdata(i, "Available"));
            }
        }
    }

}
