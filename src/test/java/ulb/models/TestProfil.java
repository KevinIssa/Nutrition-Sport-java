package ulb.models;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class TestProfil {
    @Test
    public void TestsimpleSaveCheck() throws IOException {
        Profil profil = new Profil("xu", "jason","male",LocalDate.of(2002, 9, 22));
        profil.saveUser();
        String filePath = "src/main/resources/ulb/database/user.txt";
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, lines.get(i).trim());
        }
        Assert.assertEquals("xu", lines.get(0));
        Assert.assertEquals("jason", lines.get(1));
        Assert.assertEquals("male", lines.get(2));
        Assert.assertEquals("22 9 2002", lines.get(3));
    }
    @Test
    public void simpleSaveCheckandLoadCheck() throws IOException {
        Profil profil1 = new Profil("xu", "jason","male",LocalDate.of(2002, 9, 22));
        profil1.saveUser();
        Profil profil2 = new Profil();
        Assert.assertEquals(profil1.surname , profil2.surname);
        Assert.assertEquals(profil1.firstname , profil2.firstname);
        Assert.assertEquals(profil1.sex , profil2.sex);
        Assert.assertEquals(profil1.birthDay , profil2.birthDay);
    }
    @Test
    public void simpleaddandedit() throws IOException {
        Profil profil1 = new Profil("xu", "jason","male",LocalDate.of(2002, 9, 22));
        profil1.addWeight(LocalDate.of(2007, 6, 22), 57);
        profil1.editWeight(LocalDate.of(2007, 6, 22), 56);
        float value = profil1.getWeight(LocalDate.of(2007, 6, 22));
        Assert.assertEquals(56.0, value, 0.0001);
        profil1.addHeight(LocalDate.of(2022, 6, 5), 170);
        profil1.editHeight(LocalDate.of(2022, 6, 5), 175);
        value = profil1.getHeight(LocalDate.of(2022, 6, 5));
        Assert.assertEquals(175, value, 0.0001);
    }
    @Test(expected = IllegalStateException.class)
    public void testFunctionCrash() {
        Profil profil1 = new Profil("xu", "jason","male",LocalDate.of(2002, 9, 22));
        profil1.getWeight(LocalDate.of(2002, 9, 22));
    }
    @Test(expected = IllegalStateException.class)
    public void removeHeightTest(){
        Profil profil1 = new Profil("xu", "jason","male",LocalDate.of(2002, 9, 22));
        profil1.addHeight(LocalDate.of(2022, 6, 5), 170);
        profil1.removeHeight(LocalDate.of(2022, 6, 5));
        profil1.editHeight(LocalDate.of(2022, 6, 5),30);
    }
    @Test(expected = IllegalStateException.class)
    public void removeWeightTest(){
        Profil profil1 = new Profil("xu", "jason","male",LocalDate.of(2002, 9, 22));
        profil1.addWeight(LocalDate.of(2022, 6, 5), 170);
        profil1.removeWeight(LocalDate.of(2022, 6, 5));
        profil1.editWeight(LocalDate.of(2022, 6, 5),30);
    }
    @Test
    public void complexLoadSaveTest() throws IOException {
        Profil profil1 = new Profil("xu", "jason","male",LocalDate.of(2002, 9, 22));
        profil1.addWeight(LocalDate.of(2022, 6, 5), 70);
        profil1.addWeight(LocalDate.of(2022, 6, 6), 69);
        profil1.addWeight(LocalDate.of(2022, 6, 7), 69);
        profil1.addHeight(LocalDate.of(2022, 6, 5), 169);
        profil1.addHeight(LocalDate.of(2022, 6, 6), 170);
        profil1.addHeight(LocalDate.of(2022, 6, 7), 171);
        profil1.saveUser();
        Profil profil2 = new Profil();
        Assert.assertEquals(profil1.surname , profil2.surname);
        Assert.assertEquals(profil1.firstname , profil2.firstname);
        Assert.assertEquals(profil1.sex, profil2.sex);
        Assert.assertEquals(profil1.birthDay , profil2.birthDay);
        Assert.assertEquals(profil1.getAllHeightsDate() , profil2.getAllHeightsDate());
        Assert.assertEquals(profil1.getAllWeightsDate() , profil2.getAllWeightsDate());
        List<LocalDate> list = List.copyOf(profil1.getAllHeightsDate());
        LocalDate firstElement = list.get(0);
        list = List.copyOf(profil2.getAllHeightsDate());
        LocalDate secondElement = list.get(0);
        Assert.assertEquals(firstElement, secondElement);
    }
}