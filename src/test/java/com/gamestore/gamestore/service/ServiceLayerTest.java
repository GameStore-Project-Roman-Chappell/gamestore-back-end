package com.gamestore.gamestore.service;

import com.gamestore.gamestore.model.Console;
import com.gamestore.gamestore.model.Game;
import com.gamestore.gamestore.model.TShirt;
import com.gamestore.gamestore.repository.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    ServiceLayer service;

    ConsoleRepository consoleRepo;
    GameRepository gameRepo;
    TShirtRepository shirtRepo;
    InvoiceRepository invoiceRepo;
    ProcessingFeeRepository processRepo;
    SalesTaxRateRepository taxRepo;

    @Before
    public void setUp() throws Exception {
        setUpConsoleRepositoryMock();
        setUpGameRepositoryMock();
        setUpTShirtRepositoryMock();

        service = new ServiceLayer(consoleRepo, shirtRepo, gameRepo, invoiceRepo, taxRepo, processRepo);
    }

//    MOCK REPO setups
    private void setUpConsoleRepositoryMock() {
        consoleRepo = mock(ConsoleRepository.class);
        Console xbox = new Console(1,"Xbox", "Microsoft", "1GB", "AMD", new BigDecimal(499.99), 50);
        Console xbox2 = new Console("Xbox", "Microsoft", "1GB", "AMD", new BigDecimal(499.99), 50);

        List<Console> consoleList = new ArrayList<>();
        consoleList.add(xbox);

        doReturn(xbox).when(consoleRepo).save(xbox2);
        doReturn(Optional.of(xbox)).when(consoleRepo).findById(1);
        doReturn(consoleList).when(consoleRepo).findAll();
    }

    private void setUpGameRepositoryMock() {
        gameRepo = mock(GameRepository.class);
        Game eldenRing = new Game( 1,"Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal(59.99), 50);
        Game eldenRing2 = new Game( "Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal(59.99), 50);

        List<Game> gameList = new ArrayList<>();
        gameList.add(eldenRing);

        doReturn(eldenRing).when(gameRepo).save(eldenRing2);
        doReturn(Optional.of(eldenRing)).when(gameRepo).findById(1);
        doReturn(gameList).when(gameRepo).findAll();
    }

    private void setUpTShirtRepositoryMock() {
        shirtRepo = mock(TShirtRepository.class);
        TShirt zeldaShirt = new TShirt(1,"Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);
        TShirt zeldaShirt2 = new TShirt("Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);

        List<TShirt> shirtList = new ArrayList<>();
        shirtList.add(zeldaShirt);

        doReturn(zeldaShirt).when(shirtRepo).save(zeldaShirt2);
        doReturn(Optional.of(zeldaShirt)).when(shirtRepo).findById(1);
        doReturn(shirtList).when(shirtRepo).findAll();
    }

//    CONSOLE CRUD tests
    @Test
    public void shouldSaveConsole() {
        Console consoleToSave = new Console("Xbox", "Microsoft", "1GB", "AMD", new BigDecimal(499.99), 50);
        Console expectedConsole = new Console(1,"Xbox", "Microsoft", "1GB", "AMD", new BigDecimal(499.99), 50);

        Console actualResult = service.saveConsole(consoleToSave);

        assertEquals(expectedConsole, actualResult);
    }

    @Test
    public void shouldFindAllConsoles() {
        List<Console> fromService = service.findAllConsoles();

        assertEquals(1, fromService.size());
    }

    @Test
    public void shouldFindConsoleById() {
        Console expectedResult = new Console(1,"Xbox", "Microsoft", "1GB", "AMD", new BigDecimal(499.99), 50);
        Console actualResult = service.findConsole(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldUpdateConsole() {
        Console expectedConsole = new Console(1,"PS5", "Sony", "1GB", "AMD", new BigDecimal(499.99), 50);
         service.updateConsole(new Console(1, "PS5", "Sony", "1GB", "AMD", new BigDecimal(499.99), 50));

         verify(consoleRepo).save(expectedConsole);
    }

    @Test
    public void shouldDeleteConsole() {
        service.deleteConsole(1);
        verify(consoleRepo).deleteById(1);
    }

//    GAME CRUD tests
    @Test
    public void shouldSaveGame() {
        Game gameToSave = new Game("Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal(59.99), 50);
        Game expectedGame = new Game(1,"Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal(59.99), 50);

        Game actualResult = service.saveGame(gameToSave);
        assertEquals(expectedGame, actualResult);
    }

    @Test
    public void shouldFindAllGames() {
        List<Game> fromService = service.findAllGames();

        assertEquals(1, fromService.size());
    }

    @Test
    public void shouldFindGameById() {
        Game expectedResult = new Game(1,"Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal(59.99), 50);
        Game actualResult = service.findGame(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldUpdateGame() {
        Game expectedGame = new Game(1,"Dark Souls 3", "M", "Action-Adventure", "FromSoftware", new BigDecimal(59.99), 50);
        service.updateGame(new Game(1,"Dark Souls 3", "M", "Action-Adventure", "FromSoftware", new BigDecimal(59.99), 50));

        verify(gameRepo).save(expectedGame);
    }

    @Test
    public void shouldDeleteGame() {
        service.deleteGame(1);
        verify(gameRepo).deleteById(1);
    }

//    SHIRT CRUD Tests
    @Test
    public void shouldSaveShirt() {
        TShirt shirtToSave = new TShirt("Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);
        TShirt expectedShirt = new TShirt(1,"Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);

        TShirt actualResult = service.saveTShirt(shirtToSave);
        assertEquals(expectedShirt, actualResult);
    }

    @Test
    public void shouldFindAllShirts() {
        List<TShirt> fromService = service.findAllTShirts();
        assertEquals(1, fromService.size());
    }

    @Test
    public void shouldFindShirtById() {
        TShirt expectedResult = new TShirt(1,"Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);
        TShirt actualResult = service.findTShirtById(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldUpdateShirt() {
        TShirt expectedShirt = new TShirt(1,"Medium", "Red", "Zelda Shirt", new BigDecimal(7.99), 50);
        service.updateTShirt(new TShirt(1,"Medium", "Red", "Zelda Shirt", new BigDecimal(7.99), 50));
        verify(shirtRepo).save(expectedShirt);
    }

    @Test
    public void shouldDeleteShirt() {
        service.deleteTShirt(1);
        verify(shirtRepo).deleteById(1);
    }


}