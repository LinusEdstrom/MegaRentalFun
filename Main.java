package com.Edstrom;

import com.Edstrom.dataBase.Inventory;
import com.Edstrom.dataBase.MemberRegistry;
import com.Edstrom.entity.*;
import com.Edstrom.exceptions.*;
import com.Edstrom.service.MembershipService;
import com.Edstrom.service.RentalService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application {


    MemberRegistry memberRegistry = MemberRegistry.getInstance();
    MembershipService membershipService = new MembershipService(memberRegistry);
    Inventory inventory = new Inventory();
    RentalService rentalService = new RentalService(inventory);
    //fillMemberList(membershipService);

    TableView<Member> memberTable;
    TableView<Item> itemTable;
    TableView<Rental> activeRentalsTable;
    TextField itemIdInput, nameInput, statusLevelInput, idInput, titleInput, basePriceInput;
    ComboBox<StatusLevel>statusLevelBox;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Lägger in tableView för activeRentals här

        TableColumn<Rental, String> memberColumn;
        TableColumn<Rental, String> itemColumn;
        TableColumn<Rental, LocalDate> rentDateColumn;
        TableColumn<Rental, LocalDate> returnDateColumn;

        activeRentalsTable = new TableView<>();
        activeRentalsTable.setId("activeRentalsTable");
        activeRentalsTable.setMaxHeight(300);

        memberColumn = new TableColumn<>("Member");
        itemColumn = new TableColumn<>("Item");
        rentDateColumn = new TableColumn<>("Rent date");
        returnDateColumn = new TableColumn<>("Return date");

        //Fixar lite minWidth
        memberColumn.setMinWidth(150);
        itemColumn.setMinWidth(200);


        //SetcellValueFactory för activeRentals
        memberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMember().getName())
        );
        itemColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getItem().getTitle())
        );
        rentDateColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getRentingDate())
        );
        returnDateColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getReturnDate())
        );
        activeRentalsTable.getColumns().addAll(memberColumn, itemColumn, rentDateColumn, returnDateColumn);

        activeRentalsTable.setItems(rentalService.getActiveRentals());

        //itemTable
        itemTable = new TableView();
        itemTable.setEditable(true);    //Ändra i items

        //TODO FilteredList
        FilteredList<Item> filteredItems = new FilteredList<>(rentalService.getItems(), item ->true);
        itemTable.setItems(filteredItems);

        ComboBox<String> filterBox = new ComboBox<>();
        filterBox.getItems().addAll("Filter Movies", "Action", "RomCom");
        filterBox.setValue("Filter Movies");

        filterBox.setOnAction(eventSubs -> {
                    System.out.println("Filter selected: " + filterBox.getValue());
                    String filter = filterBox.getValue();
                    filteredItems.setPredicate(item -> {

               if("Action".equals(filter)) return item instanceof Action;
               if("RomCom".equals(filter)) return item instanceof RomCom;

                return true;

        });
        });

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
        lengthColumn.setCellValueFactory(cellData -> {
            Item subItem = cellData.getValue();
            if (subItem instanceof Action) return new ReadOnlyObjectWrapper<>(((Action) subItem).getLength());
            if (subItem instanceof RomCom) return new ReadOnlyObjectWrapper<>(((RomCom) subItem).getLength());
            return new ReadOnlyObjectWrapper<>(null);
        });
        // Make subColumns editable
        makeEditableIntColumn(itemTable, lengthColumn, (subItem, value) -> {
            if (subItem instanceof Action) ((Action) subItem).setLength(value);
            else if (subItem instanceof RomCom) ((RomCom) subItem).setLength(value);
        });

        //Action column
        TableColumn<Item, Integer> explosionsColumn = new TableColumn<>("Explosions");
        explosionsColumn.setMinWidth(100);
        explosionsColumn.setCellValueFactory(cellData -> {
            Item subItem = cellData.getValue();
            return subItem instanceof Action ? new ReadOnlyObjectWrapper<>(((Action) subItem).getExplosions()) : null;
        });
        makeEditableIntColumn(itemTable, explosionsColumn, (subItem, value) -> {
            if (subItem instanceof Action) ((Action) subItem).setExplosions(value);
        });
        TableColumn<Item, Integer> coolOnelinersColumn = new TableColumn<>("Cool oneliners");
        coolOnelinersColumn.setMinWidth(140);
        coolOnelinersColumn.setCellValueFactory(cellData -> {
            Item subItem = cellData.getValue();
            return subItem instanceof Action ? new ReadOnlyObjectWrapper<>(((Action) subItem).getCoolOneliners()) : null;
        });
        makeEditableIntColumn(itemTable, coolOnelinersColumn, (subItem, value) -> {
            if (subItem instanceof Action) ((Action) subItem).setCoolOneliners(value);
        });
        //RomCom column
        TableColumn<Item, Integer> cheezinessColumn = new TableColumn<>("Cheeziness");
        cheezinessColumn.setMinWidth(100);
        cheezinessColumn.setCellValueFactory(cellData -> {
            Item subItem = cellData.getValue();
            return subItem instanceof RomCom ? new ReadOnlyObjectWrapper<>(((RomCom) subItem).getCheeziness()) : null;
        });
        makeEditableIntColumn(itemTable, cheezinessColumn, (subItem, value) -> {
            if (subItem instanceof RomCom) ((RomCom) subItem).setCheeziness(value);
        });
        TableColumn<Item, Integer> hunksColumn = new TableColumn<>("Hunks");
        hunksColumn.setMinWidth(100);
        hunksColumn.setCellValueFactory(cellData -> {
            Item subItem = cellData.getValue();
            return subItem instanceof RomCom ? new ReadOnlyObjectWrapper<>(((RomCom) subItem).getHunks()) : null;
        });
        makeEditableIntColumn(itemTable, hunksColumn, (subItem, value) -> {
            if (subItem instanceof RomCom) ((RomCom) subItem).setHunks(value);
        });


        //Make columns editable with method makeEditableColumn
        makeEditableIntColumn(itemTable, itemIdColumn, Item::setId);
        makeEditableStringColumn(itemTable, titleColumn, Item::setTitle);
        //this.<Item>
        makeEditableDoubleColumn(itemTable, basePriceColumn, Item::setBasePrice);

        itemTable.getColumns().addAll(itemIdColumn, titleColumn, basePriceColumn, lengthColumn, explosionsColumn,
                coolOnelinersColumn, cheezinessColumn, hunksColumn);

        //MemberTable
        memberTable = new TableView();
        memberTable.setEditable(true);
        memberTable.getSelectionModel().setCellSelectionEnabled(true);//för att välja i combobox

        //Inputs textfields
        //Id
        itemIdInput = new TextField();
        itemIdInput.setPromptText("ID");
        itemIdInput.setMinWidth(80);
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
        subComboBox.setPromptText("genre");
        TextField extra1 = new TextField(); // Båda har samma
        TextField extra2 = new TextField();
        TextField extra3 = new TextField();

        extra1.setPromptText("Length");
        extra2.setPromptText("Extra2");
        extra3.setPromptText("Extra3");

        subComboBox.setOnAction(subEvent -> {
            String type = subComboBox.getValue();
            if ("Action".equals(type)) {
                extra1.setPromptText("Length");
                extra2.setPromptText("Explosions");
                extra3.setPromptText("Cool Oneliners");
            } else if ("RomCom".equals(type)) {
                extra1.setPromptText("Length");
                extra2.setPromptText("Cheeziness");
                extra3.setPromptText("Hunks");
            } else {
                extra1.setPromptText("Extra1");
                extra2.setPromptText("Extra2");
                extra3.setPromptText("Extra3");
            }
        });
        // itemAddButton
        Button itemAddButton = new Button("Add Item");
        itemAddButton.setId("itemAddButton");
        itemAddButton.setOnAction(itemAdder -> {
                    try {
                        int id = Integer.parseInt(itemIdInput.getText().trim());
                        String title = titleInput.getText().trim();
                        double basePrice = Double.parseDouble(basePriceInput.getText().trim());
                        String subType = subComboBox.getValue();

                        int valueExtra1 = getInt(extra1);
                        int valueExtra2 = getInt(extra2);
                        int valueExtra3 = getInt(extra3);

                        rentalService.addItem(id, title, basePrice, subType, valueExtra1, valueExtra2, valueExtra3);

                        Stream.of(itemIdInput, titleInput, basePriceInput, extra1, extra2, extra3)
                                .forEach(TextInputControl::clear);
                        subComboBox.getSelectionModel().clearSelection();
                        showAlert(Alert.AlertType.INFORMATION, "Item added", title + " succesfully added to your inventory");

                    } catch (NumberFormatException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Numeric fields must be numbers");
                    } catch (AlreadyExistsException e) {
                        showAlert(Alert.AlertType.ERROR, "Error ID", "that item ID already exists");
                    } catch (InvalidItemDataException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "please fill everything correct");
                    } catch (NumberOverZeroException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Number values have to be positive");
                    }catch (MissingSubTypeException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Please choose what kind of movie it is");
                    } catch (Exception e) {
                        showAlert(Alert.AlertType.ERROR, "Unexpected error", e.getMessage());
                        e.printStackTrace();
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
            int newValue = event.getNewValue();
            idChangedMember.setId(newValue);
            membershipService.updateMember(idChangedMember);
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
            String newValue = event.getNewValue();
            changedMember.setName(newValue);
            membershipService.updateMember(changedMember);
            memberTable.refresh();
        });
        nameColumn.setEditable(true);   //Hit ner yoyo
        //StatusLevel
        TableColumn<Member, StatusLevel> statusLevelColumn = new TableColumn<>("Statuslevel");
        statusLevelColumn.setMinWidth(100);
        statusLevelColumn.setCellValueFactory(new PropertyValueFactory<>("statusLevel"));
        statusLevelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(StatusLevel.values())
        );
        memberTable.setEditable(true);
        statusLevelColumn.setEditable(true);

        statusLevelColumn.setOnEditCommit(event ->{
            Member newStatusMember = event.getRowValue();
            StatusLevel newStatusLevel = event.getNewValue();

            newStatusMember.setStatusLevel(newStatusLevel);
            membershipService.updateMember(newStatusMember);
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
        statusLevelBox = new ComboBox<>();
        statusLevelBox.getItems().addAll(StatusLevel.values());
        statusLevelBox.setPromptText("Status Level");
        statusLevelBox.setMinWidth(100);

        //Buttons

        //Rent button
        Button rentButton = new Button("Rent movie");
        rentButton.setId("rentButton");
        rentButton.setOnAction(rentEvent -> rentButtonClicked());

        //Return button
        Button returnButton = new Button("Return movie");
        returnButton.setId("returnButton");
        returnButton.setOnAction(event -> returnButtonClicked());

        //Add member
        Button addButton = new Button("Add member");
        addButton.setId("addButton");
        addButton.setOnAction(event -> addButtonClicked());

        //Delete member
        Button deleteButton = new Button("Remove member");
        deleteButton.setId("deleteButton");
        deleteButton.setOnAction(event -> deleteButtonClicked());

        //historyButton
        Button historyButton = new Button("See members rental history");
        historyButton.setOnAction(event -> historyButtonClicked());

        //TotalRevenueButton
        Button totalRevenueButton = new Button("See total revenue");
        totalRevenueButton.setOnAction(even -> totalRevenueButtonClicked());

        //ExitButton
        Button exitButton = new Button("Exit");
        exitButton.setId("exitButton");
        exitButton.setOnAction(exit -> {
           Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "You sure u want to exit Wigells Retro Rentals ?",
            ButtonType.YES, ButtonType.CANCEL);
            exitAlert.setTitle("Exit");
            exitAlert.setHeaderText(null);

            exitAlert.showAndWait()
                    .filter(ButtonType.YES::equals)
                    .ifPresent(yes -> Platform.exit());
        });

        VBox memberBox = new VBox();
        memberBox.setPadding(new Insets(80, 10, 10 , 10));
        memberBox.setSpacing(30);
        memberBox.getChildren().addAll(rentButton, historyButton);

        HBox itemBox = new HBox();
        itemBox.setPadding(new Insets(10, 10, 10, 10));
        itemBox.setSpacing(10);
        itemBox.getChildren().addAll( itemIdInput, titleInput, basePriceInput,
                subComboBox, extra1, extra2, extra3, itemAddButton, filterBox);

        Label movieLabel = new Label("Rentable movies");
        movieLabel.setId("movieLabel");
        movieLabel.setMaxWidth(Double.MAX_VALUE);
        movieLabel.setAlignment(Pos.CENTER);

        Label memberLabel = new Label("Members");
        memberLabel.setId("memberLabel");
        memberLabel.setMaxWidth(Double.MAX_VALUE);
        memberLabel.setAlignment(Pos.CENTER);

        Label activeRentalsLabel = new Label("Allready rented movies");
        activeRentalsLabel.setId("activeRentalsLabel");
        activeRentalsLabel.setMaxWidth(Double.MAX_VALUE);
        activeRentalsLabel.setAlignment(Pos.CENTER);

        VBox itemVbox = new VBox();
        itemVbox.setPadding(new Insets(10));
        itemVbox.setSpacing(10);
        itemVbox.getChildren().addAll(movieLabel, itemTable , itemBox);

        HBox addMemberHbox = new HBox();
        addMemberHbox.setPadding(new Insets(10,10,10,10));
        addMemberHbox.setSpacing(10);
        addMemberHbox.getChildren().addAll(idInput, nameInput, statusLevelBox, addButton);

        VBox exitBox = new VBox();
        exitBox.setPadding(new Insets(30, 10, 10, 10));
        exitBox.setSpacing(30);
        exitBox.setAlignment(Pos.BASELINE_RIGHT);

        exitBox.getChildren().addAll(deleteButton, totalRevenueButton, exitButton);

        VBox memberTableVbox = new VBox();
        memberTableVbox.setPadding(new Insets (10));
        memberTableVbox.setSpacing(10);
        memberTableVbox.getChildren().addAll(memberLabel, memberTable, addMemberHbox);

        VBox activeRentalsVbox = new VBox();
        activeRentalsVbox.setPadding(new Insets(10));
        activeRentalsVbox.setSpacing(10);
        activeRentalsVbox.getChildren().addAll(activeRentalsLabel, activeRentalsTable);



        VBox returnVbox = new VBox();
        returnVbox.setPadding(new Insets(60, 10, 10, 10));
        returnVbox.setSpacing(10);
        returnVbox.getChildren().addAll(returnButton);

        Region emptySpace = new Region();
        HBox.setHgrow(emptySpace, Priority.ALWAYS);

        HBox rentedHbox = new HBox();
        rentedHbox.setPadding(new Insets(10));
        rentedHbox.getChildren().addAll(activeRentalsVbox, returnVbox, emptySpace, exitBox);

        Label welcome = new Label("Welcome to Wigells Retro Rentals!");
        welcome.setMaxWidth(Double.MAX_VALUE);
        welcome.setAlignment(Pos.CENTER);
        welcome.setId("topLabel");

        BorderPane borderPane = new BorderPane();
        borderPane.setId("borderPane");
        borderPane.setTop(welcome);
        borderPane.setLeft(itemVbox);
        borderPane.setCenter(memberBox);
        borderPane.setRight(memberTableVbox);
        borderPane.setBottom(rentedHbox);


        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("TableView height: " + activeRentalsTable.getHeight());
    }

    public void rentButtonClicked() {
        Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
        Item selectedItem = itemTable.getSelectionModel().getSelectedItem();

        if (selectedMember == null || selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "Error", "U have to select booth a member and an item!");
            return;
        }
        //TODO Gör en liten ruta för att välja antal dagar
        TextInputDialog daysToRent = new TextInputDialog("1");
        daysToRent.setTitle("Number of days");
        daysToRent.setHeaderText(null);
        daysToRent.setContentText("Days:");

        Optional<String> stringDays = daysToRent.showAndWait();
        if (!stringDays.isPresent()) {
            showAlert(Alert.AlertType.INFORMATION, "Interrupted", "Member canceled the rent");
            return;
        }
        int days;
        try {
            days = Integer.parseInt(stringDays.get());
            if (days <= 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Number has to be posivite");
                return;
            }
            if (days > 7) {
                showAlert(Alert.AlertType.INFORMATION, "Sorry!", "Max renting time is seven days");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Enter a valid number please");
            return;
        }
        Rental rental = rentalService.rentItem(selectedMember, selectedItem, days);

        if (rental == null) {
            showAlert(Alert.AlertType.WARNING, "Oh no!", "It is allready rented!");
            return;
        } else {
           // itemTable.getItems().remove(selectedItem);  // Ta bort det från tableView när de är uthyrt
            itemTable.refresh();
            showAlert(Alert.AlertType.INFORMATION, "Rent done!", selectedMember.getName() + " rented " +
                    selectedItem.getTitle() + " for " + days + " days!\n To pay " + String.format("%.2f", rental.getTotalPrice()));
        }
    }

    public void returnButtonClicked() {
        Rental selectedRental = activeRentalsTable.getSelectionModel().getSelectedItem();
        if (selectedRental == null) {
            showAlert(Alert.AlertType.ERROR, "ERROR", "Select a movie to return please");
            return;
        }
        boolean returnedRental = rentalService.returnRental(selectedRental);
        if (returnedRental) {
            activeRentalsTable.refresh();
            itemTable.refresh();

            showAlert(Alert.AlertType.INFORMATION, "Return done!", selectedRental.getItem().getTitle() + " returned by " + selectedRental.getMember().getName());
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Return failed");
        }
    }

        public void addButtonClicked() {
        try {
            int id = Integer.parseInt(idInput.getText().trim());
            membershipService.addMember(
                    id,
                    nameInput.getText(),
                    statusLevelBox.getValue()
            );
            idInput.clear();
            nameInput.clear();
            statusLevelBox.getSelectionModel().clearSelection();
        }catch(NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID error", "ID must be a number");
        }catch(NumberOverZeroException e) {
            showAlert(Alert.AlertType.ERROR, "No negative ID:s", "ID must be over 0");
        } catch (InvalidMemberDataException e) {
            showAlert(Alert.AlertType.ERROR, "Member error", "please fill out the form properly");
        } catch (AlreadyExistsException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid ID", "ID allready exists");
        }catch (MissingStatusException e){
            showAlert(Alert.AlertType.INFORMATION, "No status", "please select a status");
        } catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }

    public void deleteButtonClicked() {
        ObservableList<Member> selectedMember = memberTable.getSelectionModel().getSelectedItems();
        ObservableList<Member> allMembers = memberTable.getItems();

        if (selectedMember == null || selectedMember.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No member selected", "Select a member to delete first");
            return;
        }
        try {
            membershipService.deleteMember(selectedMember);
            showAlert(Alert.AlertType.INFORMATION, "Deleted", "Selected member removed from area");
        } catch (InvalidMemberDataException e) {
            showAlert(Alert.AlertType.WARNING, "Error ", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", " Failed to delete member " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void historyButtonClicked() {
        Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
        if (selectedMember == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No member selected");
            return;
        }

        ObservableList<Rental> history = selectedMember.getRentalHistory();
        if (history == null || history.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No rentalhistory", "this member hasn't been active yet");
            return;
        }
        String historyString = history.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        TextArea memberHistory = new TextArea(historyString);
        memberHistory.setEditable(false);
        memberHistory.setWrapText(true);

        Alert historyAlert = new Alert(Alert.AlertType.INFORMATION);
        historyAlert.setTitle("Rental history");
        historyAlert.setHeaderText(selectedMember.getName() + " rental history");
        historyAlert.getDialogPane().setContent(memberHistory);
        historyAlert.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void totalRevenueButtonClicked() {
    double totalRevenue = rentalService.getTotalRevenue(memberTable.getItems());
    showAlert(Alert.AlertType.INFORMATION, "Total revenue", String.format("%.2f ", totalRevenue));
    }

    //Helper to safely parse numbers    // Får inte ligga i lamdan i itemAddButton
    private int getInt(TextField numberId){
        String text = numberId.getText();
        return (text == null || text.trim().isEmpty()) ? 0 : Integer.parseInt(text.trim());
    }

    private <T> void makeEditableStringColumn(
            TableView<T> table,
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
}






