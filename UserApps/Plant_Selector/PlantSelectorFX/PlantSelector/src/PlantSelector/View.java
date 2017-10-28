package PlantSelector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class View {
	ObservableList<Plant> allPlantsList;
	@FXML ListView<Plant> displayList;
	@FXML ComboBox<String> name;
	@FXML ComboBox<String> genus;
	@FXML ComboBox<String> species;
	@FXML ComboBox<String> varietal;
	@FXML ComboBox<String> layer;
	@FXML ComboBox<String> cold;
	@FXML ComboBox<String> hot;
	@FXML ComboBox<String> dry;
	@FXML ComboBox<String> wet;
	@FXML ComboBox<String> sun;
	@FXML ComboBox<String> lifespan;
	@FXML ComboBox<String> seasonality;
	@FXML ComboBox<String> leaves;
	@FXML ComboBox<String> propagation;
	@FXML ComboBox<String> wind;
	@FXML ComboBox<String> ph;
	@FXML ComboBox<String> n;
	@FXML ComboBox<String> p;
	@FXML ComboBox<String> k;
	@FXML ComboBox<String> drainage;
	@FXML ComboBox<String> product;
	
	public void initialize(){
		allPlantsList=FXCollections.observableArrayList();
		
		try(Scanner reader=new Scanner(new File("plants.csv"))){
		//try(Scanner reader=new Scanner(getClass().getResourceAsStream("plants.csv"))){
			reader.nextLine();
			while(reader.hasNextLine()){
			String[] plantData=reader.nextLine().split(",");
			allPlantsList.add(new Plant(
										plantData[0],plantData[1],plantData[2],plantData[3],plantData[4],
										plantData[5],plantData[6],plantData[7],plantData[8],plantData[9],
										plantData[10],plantData[11],plantData[12],plantData[13],plantData[14],
										plantData[15],plantData[16],plantData[17],plantData[18],plantData[19],
										plantData[20]
									   ));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		//FXCollections.sort(allPlantsList);
		displayList.setItems(allPlantsList);
		ObservableList<String> result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getName())))result.add(p.getName());
		result.remove("");result.add("");Collections.sort(result);
		name.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getGenus())))result.add(p.getGenus());
		result.remove("");result.add("");Collections.sort(result);
		genus.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getSpecies())))result.add(p.getSpecies());
		result.remove("");result.add("");Collections.sort(result);
		species.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getVarietal())))result.add(p.getVarietal());
		result.remove("");result.add("");Collections.sort(result);
		varietal.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getLayer())))result.add(p.getLayer());
		result.remove("");result.add("");Collections.sort(result);
		layer.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getCold())))result.add(p.getCold());
		result.remove("");result.add("");Collections.sort(result);
		cold.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getHot())))result.add(p.getHot());
		result.remove("");result.add("");Collections.sort(result);
		hot.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getDry())))result.add(p.getDry());
		result.remove("");result.add("");Collections.sort(result);
		dry.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getWet())))result.add(p.getWet());
		result.remove("");result.add("");Collections.sort(result);
		wet.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getSun())))result.add(p.getSun());
		result.remove("");result.add("");Collections.sort(result);
		sun.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getLifespan())))result.add(p.getLifespan());
		result.remove("");result.add("");Collections.sort(result);
		lifespan.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getSeasonality())))result.add(p.getSeasonality());
		result.add("");Collections.sort(result);
		seasonality.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getLeaves())))result.add(p.getLeaves());
		result.remove("");result.add("");Collections.sort(result);
		leaves.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getPropagation())))result.add(p.getPropagation());
		result.remove("");result.add("");Collections.sort(result);
		propagation.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getWind())))result.add(p.getWind());
		result.remove("");result.add("");Collections.sort(result);
		wind.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getPh())))result.add(p.getPh());
		result.remove("");result.add("");Collections.sort(result);
		ph.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getN())))result.add(p.getN());
		result.remove("");result.add("");Collections.sort(result);
		n.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getP())))result.add(p.getP());
		result.remove("");result.add("");Collections.sort(result);
		p.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getK())))result.add(p.getK());
		result.remove("");result.add("");Collections.sort(result);
		k.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getDrainage())))result.add(p.getDrainage());
		result.remove("");result.add("");Collections.sort(result);
		drainage.setItems(result);
		
		result=FXCollections.observableArrayList();
		for(Plant p:allPlantsList)if(!result.contains((p.getProduct())))result.add(p.getProduct());
		result.remove("");result.add("");Collections.sort(result);
		product.setItems(result);
		
		
	}
	@FXML public Object filter(){
		ObservableList<Plant> result=FXCollections.observableArrayList(allPlantsList);
		result=FXCollections.observableArrayList(result.stream().filter(
				p->name.getSelectionModel().getSelectedItem()==null||
				name.getSelectionModel().getSelectedItem().equals("") ||
				p.getName().equals(name.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->genus.getSelectionModel().getSelectedItem()==null||
				genus.getSelectionModel().getSelectedItem().equals("") ||
				p.getGenus().equals(genus.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->species.getSelectionModel().getSelectedItem()==null||
				species.getSelectionModel().getSelectedItem().equals("") ||
				p.getSpecies().equals(species.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->varietal.getSelectionModel().getSelectedItem()==null||
						varietal.getSelectionModel().getSelectedItem().equals("") ||
				p.getVarietal().equals(varietal.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->layer.getSelectionModel().getSelectedItem()==null||
				layer.getSelectionModel().getSelectedItem().equals("") ||
				p.getLayer().equals(layer.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->cold.getSelectionModel().getSelectedItem()==null||
				cold.getSelectionModel().getSelectedItem().equals("") ||
				p.getCold().equals(cold.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->hot.getSelectionModel().getSelectedItem()==null||
				hot.getSelectionModel().getSelectedItem().equals("") ||
				p.getHot().equals(hot.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->dry.getSelectionModel().getSelectedItem()==null||
				dry.getSelectionModel().getSelectedItem().equals("") ||
				p.getDry().equals(dry.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->wet.getSelectionModel().getSelectedItem()==null||
				wet.getSelectionModel().getSelectedItem().equals("") ||
				p.getWet().equals(wet.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->sun.getSelectionModel().getSelectedItem()==null||
				sun.getSelectionModel().getSelectedItem().equals("") ||
				p.getSun().equals(sun.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->lifespan.getSelectionModel().getSelectedItem()==null||
				lifespan.getSelectionModel().getSelectedItem().equals("") ||
				p.getLifespan().equals(lifespan.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->seasonality.getSelectionModel().getSelectedItem()==null||
				seasonality.getSelectionModel().getSelectedItem().equals("") ||
				p.getSeasonality().equals(seasonality.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->leaves.getSelectionModel().getSelectedItem()==null||
				leaves.getSelectionModel().getSelectedItem().equals("") ||
				p.getLeaves().equals(leaves.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->propagation.getSelectionModel().getSelectedItem()==null||
				propagation.getSelectionModel().getSelectedItem().equals("") ||
				p.getPropagation().equals(propagation.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->genus.getSelectionModel().getSelectedItem()==null||
				genus.getSelectionModel().getSelectedItem().equals("") ||
				p.getGenus().equals(genus.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->wind.getSelectionModel().getSelectedItem()==null||
				wind.getSelectionModel().getSelectedItem().equals("") ||
				p.getWind().equals(wind.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->ph.getSelectionModel().getSelectedItem()==null||
				ph.getSelectionModel().getSelectedItem().equals("") ||
				p.getPh().equals(ph.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->n.getSelectionModel().getSelectedItem()==null||
				n.getSelectionModel().getSelectedItem().equals("") ||
				p.getN().equals(n.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				pl->p.getSelectionModel().getSelectedItem()==null||
				p.getSelectionModel().getSelectedItem().equals("") ||
				pl.getP().equals(p.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->k.getSelectionModel().getSelectedItem()==null||
				k.getSelectionModel().getSelectedItem().equals("") ||
				p.getK().equals(k.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->drainage.getSelectionModel().getSelectedItem()==null||
				drainage.getSelectionModel().getSelectedItem().equals("") ||
				p.getDrainage().equals(drainage.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		result=FXCollections.observableArrayList(result.stream().filter(
				p->product.getSelectionModel().getSelectedItem()==null||
				product.getSelectionModel().getSelectedItem().equals("") ||
				p.getProduct().equals(product.getSelectionModel().getSelectedItem()))
																.collect(Collectors.toList()));
		displayList.setItems(result);
		return null;
	}
}
