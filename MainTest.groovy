class MainTest extends GroovyTestCase {
    void testAufgabe() {
        assertEquals("ABB",Main.aufgabe(Main.StringToStingArray("A,AAB",",")));
    }
}
