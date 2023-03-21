package demo;

import demo.ClassUnderTest;
import demo.Container;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class MockitoDemoInjection {

    // voici un objet sur lequel nous souhaitons faire exécuter des tests
    // ClassUnderTest a un champ de type Container. Plutôt que d'utiliser une véritable instance de cette classe, nous
    // souhaitons utiliser un mock dont nous pourrons contrôler et surveiller le comportement.
    @InjectMocks
    private ClassUnderTest classUnderTest;

    // on peut déclarer les mocks directement au niveau de la classe avec l'annotation @Mock
    // si cette annotation est utilisée, il n'est pas nécessaire de faire appel à la méthode Mockito.mock pour initialiser le mock
    // à la place on appellera Mockito.openMocks une seule fois pour initialiser tous les mocks déclarés via l'annotation
    // pour injecter un mock, il est nécessaire de le déclarer de cette manière
    @Mock
    private Container mock;

    // il existe aussi l'annotation @Spy, utile pour déclarer et injecter des espions basés sur de véritables objets

    // cet objet est utilisé pour garder trace de ressources utilisées par les mocks et les nettoyer après chaque test
    private AutoCloseable closeable;

    @BeforeEach
    void openMocks() {
        // cette méthode est appelée avant chaque test
        // elle initialise le mocks et les injecte dans les objets de la classe annotée @InjectMock, dans ce cas l'objet classUnderTest
        // notez que nous n'indiquons pas quel mock est injecté dans quel champ de l'objet, c'est Mockito qui se charge
        // de déterminer cela en fonction 1) du type du mock 2) de son nom
        closeable = MockitoAnnotations.openMocks(this);

        // pour l'injection, Mockito utilise (par ordre de priorité, et selon ce qui est disponible):
        // 1) un constructeur avec un argument du type de l'objet à injecter
        // 2) un setter
        // 3) une affectation directe de l'attribut
        // Notez que Mockito est capable d'accéder aux constructeurs/setters/champs en question même s'ils sont private
    }

    @AfterEach
    void releaseMocks() throws Exception {
        // après chaque test, nettoie les ressources utilisées par les mocks
        closeable.close();
    }

    @Test
    void testWithInjectedMock() {
        // définissons le comportement du mock injecté
        Mockito.when(mock.getItem()).thenReturn("value");

        // la valeur retournée montre que c'est bien le mock qui est utilisé dans classUnderTest
        assertEquals("value", classUnderTest.returnValue());

        // on peut aussi vérifier que classUnderTest a bien fait appel à la méthode du mock
        Mockito.verify(mock).getItem();
    }
}
