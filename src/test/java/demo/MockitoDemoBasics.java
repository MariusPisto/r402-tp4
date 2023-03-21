package demo;

import demo.Container;
import demo.SimpleContainer;
import org.junit.jupiter.api.Test;

import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class MockitoDemoBasics {

    @Test
    void mocking() {

        // La méthode mock() crée, à partir d'une interface ou d'une classe, un object concret
        Container m = Mockito.mock();

        // cet objet offre toutes les méthodes publiques de sa classe ou interface
        m.clear();
        // ces méthodes ne font toutefois rien de concret
        // les méthodes qui retournent un résultat se content d'une valeur par défaut (suivant le type: null, 0, false...)
        System.out.println(m.putItem("value"));
        System.out.println(m.getItem());

        // le mock maintient un historique des interactions (méthodes appelées), il est possible de contrôler que les
        // méthodes du mock ont bien été appelées
        Mockito.verify(m).clear();
        Mockito.verify(m).putItem("value");
        Mockito.verify(m).getItem();
    }

    @Test
    void verifyFailure() {
        Container m = Mockito.mock();
        m.clear();

        // verify() émet une exception (et fait ainsi échouer le test) si l'appel spécifié n'a pas été effectué
        Mockito.verify(m).getItem();
    }

    @Test
    void verifyNoOrder() {
        Container m = Mockito.mock();
        m.clear();
        m.getItem();

        // par défaut, verify() contrôle uniquement que les interactions ont eu lieu (exactement une fois), pas leur ordre
        Mockito.verify(m).getItem();
        Mockito.verify(m).clear();
    }

    @Test
    void verifyNumberOfCalls() {
        Container m = Mockito.mock();
        m.putItem("good value");
        m.clear();
        m.getItem();
        m.clear();

        // on peut changer le mode de vérification pour contrôler le nombre d'appels (nombre exact, minimum ou maximum)
        Mockito.verify(m, Mockito.times(2)).clear();
        Mockito.verify(m, Mockito.atLeastOnce()).clear();
        Mockito.verify(m, Mockito.atLeast(2)).clear();
        Mockito.verify(m, Mockito.atMostOnce()).getItem();
        Mockito.verify(m, Mockito.never()).putItem("bad value");
    }

    @Test
    void verifyOrder() {
        Container m = Mockito.mock();
        m.putItem("first");
        m.putItem("second");
        m.putItem("third");

        // on peut aussi vérifier l'ordre des appels
        InOrder inOrder = Mockito.inOrder(m);
        inOrder.verify(m).putItem("first");
        inOrder.verify(m).putItem("third");
    }

    @Test
    void matchers() {
        Container m = Mockito.mock();
        m.putItem("value");

        // pour la vérification de méthodes avec des arguments, il est possible d'utiliser des "matchers" plutôt que des valeurs exactes
        // any() remplace n'importe quelle valeur
        Mockito.verify(m).putItem(ArgumentMatchers.any());
        // cette vérification réussit à la condition que putItem ait été appelé avec un argument de type String
        Mockito.verify(m).putItem(ArgumentMatchers.anyString());
        // cette vérification échoue car putItem n'a jamais été appelé avec un argument de type double
        Mockito.verify(m).putItem(ArgumentMatchers.anyDouble());
        // il est aussi possible de définir un matcher qui attend un objet d'une classe donnée
        Mockito.verify(m).putItem(ArgumentMatchers.any(Boolean.class));
    }

    @Test
    void stubbing() {
        Container m = Mockito.mock();

        // on peut aussi programmer le mock (stub) pour qu'une méthode retourne une valeur ou lance une exception
        Mockito.when(m.putItem("good value")).thenReturn(true);
        Mockito.when(m.putItem("bad value")).thenThrow(new IllegalArgumentException());

        assertTrue(m.putItem("good value"));
        assertThrows(
                IllegalArgumentException.class,
                () -> m.putItem("bad value")
        );
    }

    @Test
    void stubbingVoidMethod() {
        Container m = Mockito.mock();

        // si la méthode à mocker est de type void, la syntaxe est légèrement différente, mais le principe est le même
        Mockito.doThrow(new RuntimeException()).when(m).clear();

        assertThrows(
                RuntimeException.class,
                () -> m.clear()
        );
    }

    @Test
    void spying() {
        // on peut aussi utiliser mockito pour observer le comportement de véritables objets avec spy()
        SimpleContainer c = new SimpleContainer();
        SimpleContainer spy = Mockito.spy(c);

        spy.clear();

        Mockito.verify(spy).clear();

        // on peut même combiner spy() et le stubbing
        Mockito.when(spy.putItem("bad value")).thenThrow(new IllegalArgumentException());

        assertTrue(spy.putItem("good value"));
        assertThrows(
                IllegalArgumentException.class,
                () -> spy.putItem("bad value")
        );

    }

}
