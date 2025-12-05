package com.Edstrom;

import com.Edstrom.dataBase.Inventory;
import com.Edstrom.dataBase.MemberRegistry;
import com.Edstrom.entity.*;
import com.Edstrom.service.MembershipService;
import com.Edstrom.service.RentalService;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class Main extends Application {


    MemberRegistry memberRegistry = new MemberRegistry();
    MembershipService membershipService = new MembershipService(memberRegistry);
    Inventory inventory = new Inventory();
    RentalService rentalService = new RentalService(inventory);
    //fillMemberList(membershipService);

    TableView<Member> memberTable;
    TableView<Item> itemTable;
    TextField itemIdInput, nameInput, statusLevelInput, idInput, titleInput, basePriceInput;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(" Welcome to membershipclub");

        //itemTable
        itemTable = new TableView();
        itemTable.setEditable(true);    //Ändra i items

        //columns
        //Id
        TableColumn<Item, Integer> itemIdColumn = new TableColumn<>("item-Id");
        itemIdColumn.setMinWidth(150);  //set min för o inte klumpa ihop grejer.
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        //Title
        TableColumn<Item, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        //basePrice
        TableColumn<Item, Double> basePriceColumn = new TableColumn<>("Price");
        basePriceColumn.setMinWidth(150);
        basePriceColumn.setCellValueFactory(new PropertyValueFactory<>("basePrice"));

        //subColumns
        TableColumn<Item, Integer> lengthColumn = new TableColumn<>("Length minutes");
        lengthColumn.setMinWidth(100);
        lengthColumn.setCellValueFactory(cellData ->{
            Item subItem = cellData.getValue();
            if(subItem instanceof Action) return new ReadOnlyObjectWrapper<>(((Action) subItem).getLength());
            if(subItem instanceof RomCom) return new ReadOnlyObjectWrapper<>(((RomCom)subItem).getLength());
            return new ReadOnlyObjectWrapper<>(null);
        });
        // Make subColumns editable
        makeEditableIntColumn(itemTable, lengthColumn, (subItem, value) ->{
            if(subItem instanceof Action) ((Action)subItem).setLength(value);
            else if (subItem instanceof RomCom) ((RomCom)subItem).setLength(value);
        });

        //Action column
        TableColumn<Item, Integer> explosionsColumn = new TableColumn<>("Explosions");
        explosionsColumn.setMinWidth(100);
        explosionsColumn.setCellValueFactory(cellData ->{
            Item subItem = cellData.getValue();
            return subItem instanceof Action ? new ReadOnlyObjectWrapper<>(((Action)subItem).getExplosions()) : null;
        });
        makeEditableIntColumn(itemTable, explosionsColumn, (subItem, value) ->{
            if(subItem instanceof Action)((Action)subItem).setExplosions(value);
        });
        TableColumn<Item, Integer> coolOnelinersColumn = new TableColumn<>("Cool oneliners");
        coolOnelinersColumn.setMinWidth(140);
        coolOnelinersColumn.setCellValueFactory(cellData ->{
            Item subItem = cellData.getValue();
            return subItem instanceof Action ? new ReadOnlyObjectWrapper<>(((Action)subItem).getCoolOneliners()) : null;
        });
        makeEditableIntColumn(itemTable, coolOnelinersColumn, (subItem, value) ->{
            if(subItem instanceof Action) ((Action)subItem).setCoolOneliners(value);
        });
        //RomCom column
        TableColumn<Item, Integer> cheezinessColumn = new TableColumn<>("Cheeziness");
        cheezinessColumn.setMinWidth(100);
        cheezinessColumn.setCellValueFactory(cellData ->{
            Item subItem = cellData.getValue();
            return subItem instanceof RomCom ? new ReadOnlyObjectWrapper<>(((RomCom)subItem).getCheeziness()) : null;
        });
        makeEditableIntColumn(itemTable, cheezinessColumn, (subItem, value) ->{
            if (subItem instanceof RomCom) ((RomCom)subItem).setCheeziness(value);
        });
        TableColumn<Item, Integer> hunksColumn = new TableColumn<>("Hunks");
        hunksColumn.setMinWidth(100);
        hunksColumn.setCellValueFactory(cellData->{
            Item subItem = cellData.getValue();
            return subItem instanceof RomCom ? new ReadOnlyObjectWrapper<>(((RomCom)subItem).getHunks()) : null;
        });
        makeEditableIntColumn(itemTable, hunksColumn, (subItem, value)->{
            if(subItem instanceof RomCom) ((RomCom)subItem).setHunks(value);
        });


        //Make columns editable with method makeEditableColumn
        makeEditableIntColumn(itemTable, itemIdColumn, Item::setId);
        makeEditableStringColumn(itemTable, titleColumn, Item::setTitle);
        //this.<Item>
        makeEditableDoubleColumn(itemTable, basePriceColumn, Item::setBasePrice);

        itemTable.setItems(rentalService.getItems());
        itemTable.getColumns().addAll(itemIdColumn, titleColumn, basePriceColumn, lengthColumn, explosionsColumn,
                coolOnelinersColumn, cheezinessColumn, hunksColumn);

        //MemberTable
        memberTable = new TableView();
        memberTable.setEditable(true);      //För att ändra namn på en person ??

        //Inputs textfields
        //Id
        itemIdInput = new TextField();
        itemIdInput.setPromptText("ID");
        itemIdInput.setMinWidth(80);     //Behövs bara i första så följer resten bredden.
        //Title
        titleInput = new TextField();
        titleInput.setPromptText("Title");
        //Price
        basePriceInput = new TextField();
        basePriceInput.setPromptText("price");

        //TODO om jag ska ha ComboBox för subklasserna
        ComboBox<String> subComboBox = new ComboBox<>(
                FXCollections.observableArrayList("Action", "RomCom")
        );
        TextField extra1 = new TextField(); // Båda har samma
        TextField extra2 = new TextField();
        TextField extra3 = new TextField();

        extra1.setPromptText("Length");
        extra2.setPromptText("Extra2");
        extra3.setPromptText("Extra3");

        subComboBox.setOnAction(subEvent ->{
            String type = subComboBox.getValue();
            if("Action".equals(type)){
                extra1.setPromptText("Length");
                extra2.setPromptText("Explosions");
                extra3.setPromptText("Cool Oneliners");
            }else if ("RomCom".equals(type)){
                extra1.setPromptText("Length");
                extra2.setPromptText("Cheeziness");
                extra3.setPromptText("Hunks");
            }else {
                extra1.setPromptText("Extra1");
                extra2.setPromptText("Extra2");
                extra3.setPromptText("Extra3");
            }
        });
            // Add item button //TODO BUTTON SKA NER TILL BUTTONS
        Button itemAddButton = new Button("Add Item");
        itemAddButton.setOnAction(itemAdder -> {
                    try {
                        int id = Integer.parseInt(itemIdInput.getText());
                        String title = titleInput.getText();
                        double basePrice = Double.parseDouble(basePriceInput.getText());
                        String subType = subComboBox.getValue();

                        Item newItem = null;    // Först ett tomt item för vi vet inte vilken sub det är än.
                        if ("Action".equals(subType)) {
                            int length = getInt(extra1);
                            int explosions = getInt(extra2);
                            int coolOneliners = getInt(extra3);
                            newItem = new Action(id, title, basePrice, length, explosions, coolOneliners);
                        } else if ("RomCom".equals(subType)) {
                            int length = getInt(extra1);
                            int cheeziness = getInt(extra2);
                            int hunks = getInt(extra3);
                            newItem = new RomCom(id, title, basePrice, length, cheeziness, hunks);
                        } else {
                            throw new IllegalArgumentException("Error with " + subType);
                        }
                        rentalService.addItem(newItem);

                        itemIdInput.clear();
                        titleInput.clear();
                        basePriceInput.clear();
                        extra1.clear();
                        extra2.clear();
                        extra3.clear();
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error adding item " + e.getMessage());
                    }
                });


        //TODO MEMBERTABLE
        //columns
        //Id
        TableColumn<Member, Integer> idColumn = new TableColumn<>("Id number");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        //För att ändra id
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idColumn.setOnEditCommit(event -> {
            Member idChangedMember = event.getRowValue();
            idChangedMember.setId(event.getNewValue());
            memberTable.refresh();
        });
        //Name
        TableColumn<Member, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setMinWidth(150);    //newBoston körde 200
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        // För att ändra namn
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        nameColumn.setOnEditCommit(event -> {
            Member changedMember = event.getRowValue();
            changedMember.setName(event.getNewValue());
            memberTable.refresh();
        });
        nameColumn.setEditable(true);   //Hit ner yoyo
        //StatusLevel
        TableColumn<Member, String> statusLevelColumn = new TableColumn<>("Statuslevel");
        statusLevelColumn.setMinWidth(100);
        statusLevelColumn.setCellValueFactory(new PropertyValueFactory<>("statusLevel"));
        // Choices from enum
        ObservableList<String> statusChoice = FXCollections.observableArrayList(
                Arrays.stream(StatusLevel.values()).map(StatusLevel::toString).toArray(String[]::new)
        );

        statusLevelColumn.setEditable(true);
        // ComboBox for enums
        statusLevelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        }, statusChoice.toArray(new String[0])));
        // Write string back to member
        statusLevelColumn.setOnEditCommit(memberStringCellEditEvent -> {
            Member newStatusMember = memberStringCellEditEvent.getRowValue();
            String newValue = memberStringCellEditEvent.getNewValue();
            // Här kan vi utveckla nå fint Exception
            if (newValue != null) {
                newStatusMember.setStatusLevel(newValue);
            }
        });

        //add to tableView
        memberTable.setItems(membershipService.getMembers());
        memberTable.getColumns().addAll(idColumn, nameColumn, statusLevelColumn);


        //Inputs textfields
        //Id
        idInput = new TextField();
        idInput.setPromptText("Id");
        idInput.setMinWidth(60);
        //Name
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);     //Behövs bara i första så följer resten bredden.
        //StatusLevel
        statusLevelInput = new TextField();
        statusLevelInput.setPromptText("Statuslevel");

        //Buttons
        //Add member
        Button addButton = new Button("Add member");
        addButton.setOnAction(event -> addButtonClicked());

        //Delete member
        Button deleteButton = new Button("Remove member");
        deleteButton.setOnAction(event -> deleteButtonClicked());

        HBox memberBox = new HBox();
        memberBox.setPadding(new Insets(10));
        memberBox.setSpacing(10);
        memberBox.getChildren().addAll(idInput, nameInput, statusLevelInput, addButton, deleteButton);

        HBox itemBox = new HBox();
        itemBox.setPadding(new Insets(10, 10, 10, 10));
        itemBox.setSpacing(10);
        itemBox.getChildren().addAll(itemIdInput, titleInput, basePriceInput, subComboBox, extra1, extra2, extra3, itemAddButton);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(memberTable, memberBox, itemTable, itemBox);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    // TODO METODER
    public void addButtonClicked() {
        membershipService.addMember(
                Integer.parseInt(idInput.getText()),  // Här ska det in nå fina Exceptions osså
                nameInput.getText(),
                statusLevelInput.getText());
        idInput.clear();
        nameInput.clear();
        statusLevelInput.clear();
    }
    /*public void addItemClicked(){
        rentalService.addItem(
                Integer.parseInt(itemIdInput.getText()),
                titleInput.getText(),
                basePriceInput.getText());
        itemIdInput.clear();
        titleInput.clear();
        basePriceInput.clear();
    }

     */

    public void deleteButtonClicked() {
        ObservableList<Member> memberSelected, allMembers;
        allMembers = memberTable.getItems();
        memberSelected = memberTable.getSelectionModel().getSelectedItems();
        memberSelected.forEach(allMembers::remove);
    }
    //Helper to safely parse numbers    // Får inte ligga i lamdan i itemAddButton
    private int getInt(TextField numberId){
        String text = numberId.getText();
        return (text == null || text.trim().isEmpty()) ? 0 : Integer.parseInt(text.trim());
    }

    private <T> void makeEditableStringColumn(
            TableView<T> table, // itemTable     // Gör en Overload för Doubles också, lär ju ska ha en för ints osså, lööööl
            TableColumn<T, String> column,
            BiConsumer<T, String> setter
    ) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        column.setOnEditCommit(eventString -> {
            T rowData = eventString.getRowValue();
            setter.accept(rowData, eventString.getNewValue());
            table.refresh();    // Kanske inte behövs ??
        });
        column.setEditable(true);
    }
    private <T> void makeEditableIntColumn(
            TableView<T> table, // itemTable     // Gör en Overload för Doubles också, lär ju ska ha en för ints osså, lööööl
            TableColumn<T, Integer> column,
            BiConsumer<T, Integer> setter
    ) {
            column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return value == null ? "" : value.toString();
            }
            @Override
            public Integer fromString(String string) {
                try {
                    return string == null || string.isEmpty() ? 0 : Integer.parseInt(string);
                } catch (NumberFormatException e) {// Fixa Exception heina
                    return 0;
                }
                }
                }));
             column.setOnEditCommit(eventInt -> {
            T rowData = eventInt.getRowValue();
            Integer newValue = eventInt.getNewValue();
                 if(newValue !=null) {
                     setter.accept(rowData, newValue);
                 }
                table.refresh();    // Kanske inte behövs ??
                });
                column.setEditable(true);
                }
    private <T> void makeEditableDoubleColumn(
            TableView<T> table,
            TableColumn<T, Double> column,
            BiConsumer<T, Double> setter
    ) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        column.setOnEditCommit(eventDouble -> {
            T rowData = eventDouble.getRowValue();
            setter.accept(rowData, eventDouble.getNewValue());
            table.refresh();
        });
        column.setEditable(true);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<Member> getMember() {
        ObservableList<Member> members = FXCollections.observableArrayList();

        members.add(new Member(1, "FiaStina", "Student"));
        members.add(new Member(2, "LisaLasse", "Student"));
        members.add(new Member(3, "GulliGunnar", "Standard"));
        members.add(new Member(4, "StekarJanne", "Standard"));
        members.add(new Member(5, "ClaustHauler", "Premium"));
        return members;
    }
}






