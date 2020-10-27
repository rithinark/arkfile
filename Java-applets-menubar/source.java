import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;



public class First extends Applet
{
    /**
     * MIT License
     *
     * Copyright (c) 2020 Rithin Ark
     *
     * Permission is hereby granted, free of charge, to any person obtaining a copy
     * of this software and associated documentation files (the "Software"), to deal
     * in the Software without restriction, including without limitation the rights
     * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     * copies of the Software, and to permit persons to whom the Software is
     * furnished to do so, subject to the following conditions:
     *
     * The above copyright notice and this permission notice shall be included in all
     * copies or substantial portions of the Software.
     *
     * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
     * SOFTWARE.
     */
    
    
    //declaring required class ref var
    MenuBar menubar;
    Frame frame;
    Menu Filemenu,Editmenu,Formatmenu,Viewmenu,Helpmenu;
    Label statusLabel;

    /**Overriding init()
     *  The init( ) method is the first method to be called.
     *  This is where you should initialize variables.
     *  This method is called only once during the run time of your applet.
     */

    public void init(){
        frame=new Frame("Note Pad");//Frames are top level window with border and title
        menubar=new MenuBar();//MenuBar provides menubar bound to a Frame
        // Menu provide each pull-down menus in a menubar
        //---------------------menus-------------------------------
        Filemenu= new Menu("File");
        Editmenu= new Menu("Edit");
        Formatmenu= new Menu("Format");
        Viewmenu= new Menu("View");
        Helpmenu= new Menu("Help");
        //----------------------------------------------------------
        /**
         * Label are used to place text label in Frame.
         * Here statusLabel is declared for placing value ActionListener from
         * each menu and menuitem as a Label in the frame.
         */
        statusLabel=new Label();
        statusLabel.setAlignment(Label.CENTER);
        //MenuItemListener class declared below for gathering ActionListener purpose...
        MenuItemListener menuItemListener= new MenuItemListener();

        /**
         * each menu item is created and stored in map...
         * using menuItem method declared below which returns a map..
         * each menu item is then added to its respective Menus...
         */
        Map<String,MenuItem> file_menuitem=menuItem("New","New Window","Open","Save","Save AS");//enter the menuitem on ur wish
        for(String key:file_menuitem.keySet()){
            Filemenu.add(file_menuitem.get(key));
        }
        Map<String,MenuItem> edit_menuitem =menuItem("Undo","Redo","Cut","Copy","Paste");
        for(String key: edit_menuitem.keySet()){
            Editmenu.add(edit_menuitem.get(key));
        }
        Map<String,MenuItem> format_menuitem =menuItem("Word Wrap","Font");
        for(String key: format_menuitem.keySet()){
            Formatmenu.add(format_menuitem.get(key));
        }
        Map<String,MenuItem> view_menuitem =menuItem("Zoom","StatusBar");
        for(String key: view_menuitem.keySet()){
            Viewmenu.add(view_menuitem.get(key));
        }
        Map<String,MenuItem> help_menuitem =menuItem("View Help","Send FeedBack","About NotePad");
        for(String key: help_menuitem.keySet()){
            Helpmenu.add(help_menuitem.get(key));
        }

        //setting up actionListener....
        Filemenu.addActionListener(menuItemListener);
        Editmenu.addActionListener(menuItemListener);
        Formatmenu.addActionListener(menuItemListener);
        Viewmenu.addActionListener(menuItemListener);
        Helpmenu.addActionListener(menuItemListener);

        //finally all created menus are added into the menubar and menubar is added into the Frame
        menubar.add(Filemenu);
        menubar.add(Editmenu);
        menubar.add(Formatmenu);
        menubar.add(Viewmenu);
        menubar.add(Helpmenu);
        frame.setMenuBar(menubar);
        frame.add(statusLabel);
        // frame is showed
        frame.show();
        //gemotery of frame...
        frame.setSize(400,400);

    }
    //the method which returns all menuitem in map
    //String ...items-- varargs takes variable number of items---feature introduced in JDK 5...
    public Map<String,MenuItem> menuItem(String ...items){
        Map<String, MenuItem> menuitem = new LinkedHashMap<>();
                for(String item:items)
                    menuitem.put(item,new MenuItem(item));
        return menuitem;
    }
    //inner class for ActionListener purpose
    class MenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            statusLabel.setText(e.getActionCommand()+ " MenuItem clicked.");
        }
    }

}




