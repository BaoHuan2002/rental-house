package com.rentalhouse.app.views.containers.Dashboard.RevenueManagement;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import com.rentalhouse.app.controllers.InfrastructureController;
import com.rentalhouse.app.controllers.InvoiceController;
import com.rentalhouse.app.models.Invoice;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;

public class RevenueManagementScene implements Initializable {
   @FXML private AnchorPane apartment_form;
   @FXML private AnchorPane motel_form;
   @FXML private AnchorPane select_model;
   @FXML private AnchorPane wholeHouse_form;
   @FXML private Label motel_label;
   @FXML private Label apartment_label;
   @FXML private Label wholeHouse_label;
   @FXML private Button motel_back;
   @FXML private Button apartment_back;
   @FXML private Button wholeHouse_back;
   @FXML private AnchorPane anchorpane_motelContainer;
   @FXML private GridPane gridPane_motelCard;
   @FXML private Label revenueForm_year;
   @FXML private ScrollPane revenue_scrollPane;
   @FXML private Label revenueForm_totalPrice;
   @FXML private ComboBox<String> revenue_comboBoxYear;
   @FXML private ScrollPane revenue_showInvoices;
   @FXML private GridPane gridPane_invoices;
   @FXML private TextField revenue_search;
   @FXML private Label revenueForm_selectYearLabel;
   @FXML private Label revenueForm_yearLabel;
   @FXML private Button invoiceMonth_back;
   @FXML private FontAwesomeIcon font_search;
   @FXML private AnchorPane anchorpane_containInvoices;
   @FXML private Label revenueForm_revenueMonth;
   @FXML private Label revenueForm_monthYear;

   @FXML private AnchorPane anchorpane_tableViewInvoices;
   @FXML private FontAwesomeIcon changeView_list;
   @FXML private FontAwesomeIcon changeView_table;
   
   @FXML private TableColumn<Invoice, String> revenue_col_electricity;
   @FXML private TableColumn<Invoice, String> revenue_col_Electric_newNumber;
   @FXML private TableColumn<Invoice, String> revenue_col_Electric_oldNumber;
   @FXML private TableColumn<Invoice, String> revenue_col_Electric_priceTotalUsed;
   @FXML private TableColumn<Invoice, String> revenue_col_Electric_usedTotalNumber;
   @FXML private TableColumn<Invoice, String> revenue_col_Water;
   @FXML private TableColumn<Invoice, String> revenue_col_Water_newNumber;
   @FXML private TableColumn<Invoice, String> revenue_col_Water_oldNumber;
   @FXML private TableColumn<Invoice, String> revenue_col_Water_usedTotalNumber;
   @FXML private TableColumn<Invoice, String> revenue_col_Water_priceTotal;
   @FXML private TableColumn<Invoice, Date> revenue_col_date;
   @FXML private TableColumn<Invoice, String> revenue_col_infrasName;
   @FXML private TableColumn<Invoice, String> revenue_col_listedPrice;
   @FXML private TableColumn<Invoice, String> revenue_col_totalPrice;
   @FXML private TableView<Invoice> revenue_tableView;
   @FXML private Label revenueForm_notice;
   @FXML private Label revenueForm_textNotice;
   @FXML private FlowPane flowPane_cardMonth;
   @FXML private VBox vBox_cardMonth;
   private InvoiceController invoiceController;
   private String year;
   private BigDecimal totalPrice;
   public static AnchorPane cardModel;
   public static Label revenueLabel;
   private InfrastructureController infrastructureController = new InfrastructureController();
   private  List<Invoice> invoicesByMonth;

   int col = 0, row = 1;
   int cols = 0, rows = 1;

   public RevenueManagementScene() {
      this.invoiceController = new InvoiceController();
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      revenue_showInvoices.setFitToWidth(true);
      revenue_scrollPane.setFitToWidth(true);
      showYearComboBox(revenue_comboBoxYear);
      selectYear(revenue_comboBoxYear);
   }

   public static Parent getRevenueManagement() throws IOException {
      return FXMLLoader.load(RevenueManagementScene.class.getResource("revenueManagement.fxml"));
   }

   // todo CLEAR FOR GRID-PANE CARDS MONTH SHOWING MAIN BOARD REVENUE:
   public void clearPane() {
      gridPane_motelCard.getChildren().clear();
      col = 0;
      row = 1;
   }

   // todo CLEAR GRID-PANE FOR CARDS INVOICE INSIDE EACH CARD MONTH:
   public void clearPaneInvoices() {
      gridPane_invoices.getChildren().clear();
      cols = 0;
      rows = 1;
   }

   // todo SHOW UP CARDS MONTH (12 CARDS) FOR EACH YEAR WHEN SELECT COMBO-BOX SELECT YEAR:
   public void showCardMotel(String select) {
      String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
      };
      infrastructureController.GetInfrastructures().forEach(infras -> {
         flowPane_cardMonth.getChildren().clear();
         Map<String, BigDecimal> totalAmount = invoiceController.getMonthBySearch(Integer.parseInt(select), infras.getUser_id());
         BigDecimal highestAmount = BigDecimal.ZERO;
         for (BigDecimal amount : totalAmount.values()) {
            if (amount.compareTo(highestAmount) > 0) {
                  highestAmount = amount;
            }
         }
         year = getYear(totalAmount);
         for (int i = 0; i < months.length; i++) {
            String month = months[i];
            totalPrice = totalAmount.getOrDefault((i + 1) + year, BigDecimal.ZERO);
            try {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("modelCard.fxml")));
               AnchorPane modelCard = fxmlLoader.load();
               ModelCardController controller = fxmlLoader.getController();
               controller.setData(month, totalPrice);
               if (totalPrice == BigDecimal.ZERO) {
                  cardModel.setStyle("""
                        -fx-background-color: linear-gradient(to bottom right, #737373, #D9D9D9);
                        -fx-background-radius: 20;
                        -fx-cursor: DEFAULT
                        """);
                  revenueLabel.setText("$ - -");
               } else if (totalPrice.equals(highestAmount)) {
                  cardModel.setStyle("""
                        -fx-background-color: linear-gradient(to bottom right, #1f6b08, rgb(74, 202, 23));
                        -fx-background-radius: 20;
                        -fx-cursor: hand;
                        """);
               } else {
                  cardModel.setStyle("""
                        -fx-background-color: linear-gradient(to bottom right,  #100444, #0a0181);
                        -fx-background-radius: 20;
                        -fx-cursor: hand;
                        """);
               }
               clickPane(modelCard, totalPrice, (i + 1) + year, infras.getUser_id());
               flowPane_cardMonth.getChildren().add(modelCard);
               flowPane_cardMonth.setHgap(20);
               flowPane_cardMonth.setVgap(20);
            } catch (Exception e) {
               e.getMessage();
               e.printStackTrace();
            }
         }
      });
   }

   // todo SPLIT YEAR AND MONTH AFTER GET DATA:
   public String getYear(Map<String, BigDecimal> totalAmount) {
      String year = null;
      if (totalAmount != null) {
         for (String key : totalAmount.keySet()) {
            year = key.substring(key.lastIndexOf("-"), key.length());
         }
      }
      return year;
   }

   // todo ADD AND SHOW UP YEARS FOR COMBO-BOX, SELECT DEFAULT OPTION TO SHOW INFO REVENUE FOR YEAR AFTER SELECTED:
   private void showYearComboBox(ComboBox<String> selectYear) {
      List<String> years = new ArrayList<>();
      LocalDate presentYear = LocalDate.now();
      Integer currentYear = presentYear.getYear();
      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy");
      for (int i = 0; i < 25; i++) {
         LocalDate yearDate = LocalDate.of(currentYear - i, 1, 1);
         String year = df.format(yearDate);
         years.add(year);
      }
      selectYear.setItems(FXCollections.observableArrayList(years));
      revenue_comboBoxYear.getSelectionModel().select(currentYear.toString());
      showCardMotel(currentYear.toString());
      selectYear(revenue_comboBoxYear);
      infrastructureController.GetInfrastructures().forEach(infras -> {
         revenueForm_totalPrice.setText("$" + invoiceController.getTotalYear(Integer.parseInt(currentYear.toString()), infras.getUser_id()).toString());
         revenueForm_yearLabel.setText("# " + currentYear.toString() + ":");
      });
   }

   // todo HANDLE COMBO-BOX WHEN SELECT A OTHER YEAR:
   public void selectYear(ComboBox<String> selectYear) {
      selectYear.valueProperty().addListener((obs, oldValue, newValue) -> {
         flowPane_cardMonth.getChildren().clear();
         showCardMotel(newValue);

         infrastructureController.GetInfrastructures().forEach(infras -> {
            revenueForm_yearLabel.setText("# " + newValue.replace("-", "") + ":");
            if (invoiceController.getTotalYear(Integer.parseInt(newValue), infras.getUser_id()) != BigDecimal.ZERO) {
               revenueForm_totalPrice.setText("$" + invoiceController.getTotalYear(Integer.parseInt(newValue), infras.getUser_id()).toString());
            } else {
               revenueForm_totalPrice.setText("$ - -");
            }
         });
      });
   }

   // todo CHANGE VIEW FOR OPTION TABLE OR LIST VIEW:
   public void changeView(MouseEvent event) {
      if (event.getSource() == changeView_table) {
         disableChangeViewTable(false, true);
         revenue_search.setText("");
         if (anchorpane_tableViewInvoices.isVisible() && revenue_search.isVisible()) {
            searchOnTableView(invoicesByMonth);
            revenue_tableView.setFocusTraversable(true);
         }
      } else if (event.getSource() == changeView_list) {
         disableChangeViewList(false, true);
         revenue_search.setText("");
         if (anchorpane_containInvoices.isVisible() && revenue_search.isVisible()) {
            searchOnListView(invoicesByMonth);
         }
      }
   }

   // todo HANDLE FOR EACH CARD MONTH WHEN CLICK ON IT TO SHOW ALL INVOICES FOR CARD MONTH WAS CLICKED:
   public void clickPane(AnchorPane modelCard, BigDecimal revenue, String date, String userID) {
      if (revenue != BigDecimal.ZERO) {
         modelCard.setOnMouseClicked(event -> {
            revenueForm_monthYear.setText(date + " :");
            revenueForm_revenueMonth.setText("$" + revenue.toString());
            changeView_list.setDisable(true);
            changeView_list.setFill(Color.DARKGRAY);
            changeView_table.setFill(Color.valueOf("#0f2794"));
            changeView_table.setDisable(false);
            disableLabels(false, true);
            invoicesByMonth = invoiceController.getInvoicesByMonth(date, userID);
            if (invoicesByMonth != null) {
               setDataOnTable(invoicesByMonth);
               invoicesByMonth.forEach(invoice -> {
                  try {
                     FXMLLoader fxmlLoader = new FXMLLoader();
                     fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("invoiceMonth.fxml")));
                     AnchorPane invoiceCard = fxmlLoader.load();
                     InvoiceMonthController controller = fxmlLoader.getController();
                     controller.setData(invoice);
                     if (cols == 1) {
                        cols = 0;
                        rows++;
                     }
                     if (rows % 2 == 0) {
                        invoiceCard.setStyle("""
                              -fx-background-color: linear-gradient(to bottom right,  #0e1663, #0e0d4d);
                              -fx-background-radius: 10;
                              """);
                     }
                     gridPane_invoices.add(invoiceCard, cols++, rows);
                     GridPane.setMargin(invoiceCard, new Insets(0, 0, 5, 0));
                     GridPane.setHgrow(invoiceCard, Priority.ALWAYS);
                  } catch (Exception e) {
                     e.getMessage();
                     e.printStackTrace();
                  }
               });
               searchOnListView(invoicesByMonth);
            } else {
               return;
            }
         });
      }
   }

   // todo SET ALL DATA AFTER GET LIST INVOICES BY MONTH FROM DATABASE:
   public void setDataOnTable(List<Invoice> invoices) {
      revenue_col_listedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
      revenue_col_Water_priceTotal.setCellValueFactory(new PropertyValueFactory<>("water_price"));
      revenue_col_Water_oldNumber.setCellValueFactory(new PropertyValueFactory<>("OldWater_number"));
      revenue_col_Water_newNumber.setCellValueFactory(new PropertyValueFactory<>("NewWater_number"));
      revenue_col_Electric_priceTotalUsed.setCellValueFactory(new PropertyValueFactory<>("electricity_price"));
      revenue_col_Electric_oldNumber.setCellValueFactory(new PropertyValueFactory<>("OldElectricity_number"));
      revenue_col_Electric_newNumber.setCellValueFactory(new PropertyValueFactory<>("NewElectricity_number"));
      revenue_col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
      revenue_col_date.setCellValueFactory(new PropertyValueFactory<>("create_at"));
      revenue_tableView.setItems(FXCollections.observableList(invoices));
      addValueElectricUsedTotalNumber(revenue_col_Electric_usedTotalNumber);
      addValueWaterUsedTotalNumber(revenue_col_Water_usedTotalNumber);
      addValueInfrasName(revenue_col_infrasName);

      customPrice(revenue_col_totalPrice);
      customPrice(revenue_col_Water_priceTotal);
      customPrice(revenue_col_Electric_priceTotalUsed);
      customListedPrice(revenue_col_listedPrice);
      customCellNew(revenue_col_Electric_newNumber);
      customCellNew(revenue_col_Water_newNumber);
      customCellOld(revenue_col_Water_oldNumber);
      customCellOld(revenue_col_Electric_oldNumber);
      customizeCellsDate(revenue_col_date);
   }

   // todo CUSTOMIZE FOR CELL DATE:
   private void customizeCellsDate(TableColumn<Invoice, Date> date) {
      date.setCellFactory(col -> new TableCell<Invoice, Date>() {
            @Override
            public void updateItem(Date item, boolean empty) {
               super.updateItem(item, empty);
               if (item == null || empty) {
                  setText(null);
               } else {
                  setText(item.toString());
               }
               setStyle(
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 13px;" +
                        "-fx-text-fill: #0070c6;" +
                        "-fx-padding: 5 5 5 5;"
               );
            }
      });
   }

   // todo CUSTOMIZE FOR CELLS PRICE:
   private void customPrice(TableColumn<Invoice, String> price) {
      price.setCellFactory(col -> new TableCell<Invoice, String>() {
         @Override
         public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
               setText(null);
            } else {
               setText(item.toString());
            }
            setStyle(
                     "-fx-font-weight: 700;" +
                     "-fx-font-size: 13px;" +
                     "-fx-text-fill: #bc0000;" +
                     "-fx-padding: 5 5 5 5;"
            );
         }
      });
   }

   // todo CUSTOMIZE FOR CELL LISTED PRICE:
   private void customListedPrice(TableColumn<Invoice, String> price) {
      price.setCellFactory(col -> new TableCell<Invoice, String>() {
         @Override
         public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
               setText(null);
            } else {
               setText(item.toString());
            }
            setStyle(
                     "-fx-font-weight: 700;" +
                     "-fx-font-size: 13px;" +
                     "-fx-text-fill: #ff5500;" +
                     "-fx-padding: 5 5 5 5;"
            );
         }
      });
   }

   // todo CUSTOMIZE FOR CELLS OLD NUMBER:
   private void customCellOld(TableColumn<Invoice, String> oldValue) {
      oldValue.setCellFactory(col -> new TableCell<Invoice, String>() {
         @Override
         public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
               setText(null);
            } else {
               setText(item.toString());
            }
            setStyle(
                     "-fx-font-weight: 700;" +
                     "-fx-font-size: 13px;" +
                     "-fx-text-fill: #0F176F;" +
                     "-fx-padding: 5 5 5 5;"
            );
         }
      });
   }

   // todo CUSTOMIZE FOR CELLS NEW NUMBER:
   private void customCellNew(TableColumn<Invoice, String> newValue) {
      newValue.setCellFactory(col -> new TableCell<Invoice, String>() {
         @Override
         public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
               setText(null);
            } else {
               setText(item.toString());
            }
            setStyle(
                     "-fx-font-weight: 700;" +
                     "-fx-font-size: 13px;" +
                     "-fx-text-fill: #279a00;" +
                     "-fx-padding: 5 5 5 5;"
            );
         }
      });
   }

   // todo CUSTOMIZE FOR CELLS TOTAL NUMBER:
   public void addValueElectricUsedTotalNumber(TableColumn<Invoice, String> electricTotalUsed) {
      electricTotalUsed.setCellFactory(new Callback<TableColumn<Invoice, String>, TableCell<Invoice, String>>() {
         @Override
         public TableCell<Invoice, String> call(TableColumn<Invoice, String> param) {
            return new TableCell<Invoice, String>() {
                  @Override
                  protected void updateItem(String item, boolean empty) {
                     super.updateItem(item, empty);
                     if (empty) {
                        setText(null);
                     } else {
                        Invoice data = getTableView().getItems().get(getIndex());
                        String totalUsedElectNumber = String.valueOf(Integer.parseInt(data.getNewElectricity_number()) - Integer.parseInt(data.getOldElectricity_number()));
                        setText(totalUsedElectNumber);
                        setStyle("""
                              -fx-padding: 5 0 5 10;
                              -fx-font-weight: 700;
                              -fx-font-family: 'Tahoma';
                              -fx-text-fill: BLUE;
                              -fx-font-size: 14px;
                              """);
                     }
                  }
            };
         }
      });
   }

   // todo CUSTOMIZE FOR CELL TOTAL NUMBER WATER:
   public void addValueWaterUsedTotalNumber(TableColumn<Invoice, String> waterTotalUsed) {
      waterTotalUsed.setCellFactory(new Callback<TableColumn<Invoice, String>, TableCell<Invoice, String>>() {
         @Override
         public TableCell<Invoice, String> call(TableColumn<Invoice, String> param) {
            return new TableCell<Invoice, String>() {
                  @Override
                  protected void updateItem(String item, boolean empty) {
                     super.updateItem(item, empty);
                     if (empty) {
                        setText(null);
                     } else {
                        Invoice data = getTableView().getItems().get(getIndex());
                        String totalUsedWaterNumber = String.valueOf(Integer.parseInt(data.getNewWater_number()) - Integer.parseInt(data.getOldWater_number()));
                        setText(totalUsedWaterNumber);
                        setStyle("""
                              -fx-padding: 5 0 5 10;
                              -fx-font-weight: 700;
                              -fx-font-family: 'Tahoma';
                              -fx-text-fill: BLUE;
                              -fx-font-size: 14px;
                              """);
                     }
                  }
            };
         }
      });
   }

   // todo ADD MORE INFRASTRUCTURE NAME:
   public void addValueInfrasName(TableColumn<Invoice, String> infrasName) {
      infrasName.setCellFactory(new Callback<TableColumn<Invoice, String>, TableCell<Invoice, String>>() {
         @Override
         public TableCell<Invoice, String> call(TableColumn<Invoice, String> param) {
            return new TableCell<Invoice, String>() {
                  @Override
                  protected void updateItem(String item, boolean empty) {
                     super.updateItem(item, empty);
                     if (empty) {
                        setText(null);
                     } else {
                        Invoice data = getTableView().getItems().get(getIndex());
                        String infrasName = infrastructureController.getById(data.getInfrastructureID()).getName();
                        setText(infrasName);
                        setStyle("""
                              -fx-padding: 5 0 5 10;
                              -fx-font-weight: 700;
                              -fx-font-family: 'Tahoma';
                              -fx-text-fill: BLUE;
                              -fx-font-size: 14px;
                              """);
                     }
                  }
            };
         }
      });
   }

   // todo HANDLE BACK BUTTON WHEN CLICKED:
   public void back(ActionEvent e) {
      clearPaneInvoices();
      disableLabels(true, false);
      anchorpane_tableViewInvoices.setVisible(false);
   }

   // todo CREATE METHOD DISABLE TO REUSE FOR ANYWHERE WHEN NEEDED:
   public void disableLabels(Boolean OFF, Boolean ON) {
      revenueForm_selectYearLabel.setVisible(OFF);
      revenueForm_yearLabel.setVisible(OFF);
      revenue_comboBoxYear.setVisible(OFF);
      revenueForm_totalPrice.setVisible(OFF);
      vBox_cardMonth.setVisible(OFF);
      revenueForm_notice.setVisible(OFF);;
      revenueForm_textNotice.setVisible(OFF);;
      font_search.setVisible(ON);
      revenue_search.setVisible(ON);
      anchorpane_containInvoices.setVisible(ON);
      invoiceMonth_back.setVisible(ON);
      revenueForm_revenueMonth.setVisible(ON);
      revenueForm_monthYear.setVisible(ON);
      changeView_list.setVisible(ON);
      changeView_table.setVisible(ON);
   }

   // todo CREATE METHOD DISABLE TO REUSE FOR ANYWHERE:
   public void disableChangeViewTable(Boolean OFF, Boolean ON) {
      changeView_table.setDisable(ON);
      anchorpane_containInvoices.setVisible(OFF);
      anchorpane_tableViewInvoices.setVisible(ON);
      changeView_list.setDisable(OFF);
      changeView_table.setFill(Color.DARKGRAY);
      changeView_list.setFill(Color.valueOf("#0f2794"));
   }
   // todo CREATE METHOD DISABLE TO REUSE FOR ANYWHERE:
   public void disableChangeViewList(Boolean OFF, Boolean ON) {
      changeView_list.setFill(Color.DARKGRAY);
      changeView_table.setDisable(OFF);
      changeView_list.setDisable(ON);
      anchorpane_containInvoices.setVisible(ON);
      anchorpane_tableViewInvoices.setVisible(OFF);
      changeView_table.setFill(Color.valueOf("#0f2794"));
   }

   // todo SEARCH ON TABLE FOR USER CHANGED OPTION TABLE VIEW:
   public void searchOnTableView(List<Invoice> invoices) {
      ObservableList<Invoice> dataList = FXCollections.observableList(invoices);
      FilteredList<Invoice> filteredList = new FilteredList<>(dataList, p -> true);

      PauseTransition pause = new PauseTransition(Duration.millis(500));
      pause.setOnFinished(e -> revenue_tableView.setItems(filteredList));

      revenue_search.textProperty().addListener((obs, oldValue, newValue) -> {
         filteredList.setPredicate(invoice -> {
               if (newValue == null || newValue.isEmpty()) {
                  return true;
               }
               String lowerCaseFilter = newValue.toLowerCase();
               String infrasName = infrastructureController.getById(invoice.getInfrastructureID()).getName().toLowerCase();
               String totalPrice = new BigDecimal(invoice.getTotal_price()).toString();
               return infrasName.contains(lowerCaseFilter) || totalPrice.contains(lowerCaseFilter);
         });
         pause.playFromStart();
      });
   }

   // todo SEARCH ON LIST FOR USER CHANGED OPTION LIST VIEW:
   public void searchOnListView(List<Invoice> invoices) {
      ObservableList<Invoice> dataList = FXCollections.observableList(invoices);
      FilteredList<Invoice> filteredList = new FilteredList<>(dataList, p -> true);
      PauseTransition pause = new PauseTransition(Duration.millis(0));

      pause.setOnFinished(e -> {
            clearPaneInvoices();
            for (Invoice invoice : filteredList) {
               try {
                  FXMLLoader fxmlLoader = new FXMLLoader();
                  fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("invoiceMonth.fxml")));
                  AnchorPane invoiceCard = fxmlLoader.load();
                  InvoiceMonthController controller = fxmlLoader.getController();
                  controller.setData(invoice);

                  if (cols == 1) {
                        cols = 0;
                        rows++;
                  }

                  if (rows % 2 == 0) {
                        invoiceCard.setStyle("""
                           -fx-background-color: linear-gradient(to bottom right,  #0e1663, #0e0d4d);
                           -fx-background-radius: 10;
                           """);
                  }

                  gridPane_invoices.add(invoiceCard, cols++, rows);
                  GridPane.setMargin(invoiceCard, new Insets(0, 0, 5, 0));
                  GridPane.setHgrow(invoiceCard, Priority.ALWAYS);
               } catch (Exception ex) {
                  ex.printStackTrace();
               }
            }
      });
      revenue_search.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredList.setPredicate(invoice -> {
               if (newValue == null || newValue.isEmpty()) {
                  return true;
               }
               String lowerCaseFilter = newValue.toLowerCase();
               String infrasName = infrastructureController.getById(invoice.getInfrastructureID()).getName().toLowerCase();
               String totalPrice = new BigDecimal(invoice.getTotal_price()).toString();
               return infrasName.contains(lowerCaseFilter) || totalPrice.contains(newValue);
            });
            pause.playFromStart();
      });
   }

   public void searchOnListViewWithoutPauseTransition(List<Invoice> invoices) {
      ObservableList<Invoice> dataList = FXCollections.observableList(invoices);
      FilteredList<Invoice> filteredList = new FilteredList<>(dataList, p -> true);
      revenue_search.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredList.setPredicate(invoice -> {
               if (newValue == null || newValue.isEmpty()) {
                  return true;
               }
               String lowerCaseFilter = newValue.toLowerCase();
               String infrasName = infrastructureController.getById(invoice.getInfrastructureID()).getName().toLowerCase();
               return infrasName.contains(lowerCaseFilter);
            });
         
            clearPaneInvoices();
            for (Invoice invoice : filteredList) {
               try {
                  FXMLLoader fxmlLoader = new FXMLLoader();
                  fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("invoiceMonth.fxml")));
                  AnchorPane invoiceCard = fxmlLoader.load();
                  InvoiceMonthController controller = fxmlLoader.getController();
                  controller.setData(invoice);

                  if (cols == 1) {
                     cols = 0;
                     rows++;
                  }

                  if (rows % 2 == 0) {
                     invoiceCard.setStyle("""
                        -fx-background-color: linear-gradient(to bottom right,  #0e1663, #0e0d4d);
                        -fx-background-radius: 10;
                        """);
                  }

                  gridPane_invoices.add(invoiceCard, cols++, rows);
                  GridPane.setMargin(invoiceCard, new Insets(0, 0, 5, 0));
                  GridPane.setHgrow(invoiceCard, Priority.ALWAYS);
                  GridPane.setVgrow(invoiceCard, Priority.ALWAYS);
               } catch (Exception ex) {
                  ex.printStackTrace();
               }
            }
      });
   }
}
