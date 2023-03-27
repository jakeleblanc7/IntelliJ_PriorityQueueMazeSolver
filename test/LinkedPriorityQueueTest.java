import org.junit.jupiter.api.*;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedPriorityQueueTest {

    private PriorityQueue<String> classUnderTest;
    private PriorityQueue<String> preState;

    @BeforeEach
    void newPriorityQueue() {
        classUnderTest = new LinkedPriorityQueue<>();
        preState = new LinkedPriorityQueue<>();
    }


    @Nested
    class WhenNewEmpty {

        @Test
        @DisplayName("New or empty PriorityQueue isEmpty returns true.")
        void isEmpty_empty_returnsTrue() {
            assertTrue(classUnderTest.isEmpty());
        }

        @Test
        @DisplayName("Empty Queue returns size of 0.")
        void size_empty_returnsZero() {
            assertEquals(0, classUnderTest.size());
        }

        @Test
        @DisplayName("Empty first throws NoSuchElementException.")
        void first_empty_throwException() {
            assertThrows(NoSuchElementException.class, () -> classUnderTest.first());
        }

        @Test
        @DisplayName("Dequeueing from an empty Queue throws NoSuchElementException.")
        void dequeue_empty_throwException() {
            assertThrows(NoSuchElementException.class, () -> classUnderTest.dequeue());
        }

        @Test
        @DisplayName("Empty Queue toString returns '<-- front'.")
        void toString_empty_stringFront() {
            assertEquals("<-- front", classUnderTest.toString());
        }

        @Nested
        class WhenSingleton {

            @BeforeEach
            void enqueueSingleElement() {
                classUnderTest.enqueue("C", 10);
                preState.enqueue("C", 10);
            }

            @Test
            @DisplayName("Singleton Queue isEmpty returns false.")
            void isEmpty_singleton_returnsFalse() {
                assertFalse(classUnderTest.isEmpty());
            }

            @Test
            @DisplayName("Singleton Queue returns size of 1.")
            void size_singleton_returnsOne() {
                assertEquals(1, classUnderTest.size());
            }

            @Test
            @DisplayName("Enqueueing a PriorityNode with lower priority changes front.")
            void enqueue_lowerPriority_updatesFront() {
                classUnderTest.enqueue("J", 8);
                assertEquals("J", classUnderTest.first());
            }

            @Test
            @DisplayName("Enqueueing a PriorityNode with higher priority doesn't change front.")
            void enqueue_higherPriority_unchangedFront() {
                classUnderTest.enqueue("L", 16);
                assertEquals("C", classUnderTest.first());
            }

            @Test
            @DisplayName("Checking first on a singleton Queue returns front.")
            void first_singleton_returnsFront() {
                assertEquals("C", classUnderTest.first());
            }

            @Test
            @DisplayName("First on a singleton Queue results in unchanged Queue.")
            void first_singleton_unchanged() {
                classUnderTest.first();
                assertEquals(preState, classUnderTest);
            }

            @Test
            @DisplayName("Dequeueing from a singleton Queue returns Front.")
            void dequeue_singleton_returnsFront() {
                assertEquals("C", classUnderTest.dequeue());
            }

            @Test
            @DisplayName("Dequeueing a singleton Queue results in an empty Queue.")
            void dequeue_singleton_emptyPriorityQueue() {
                classUnderTest.dequeue();
                assertEquals(new LinkedPriorityQueue<>(), classUnderTest);
            }

            @Test
            @DisplayName("Singleton Queue toString returns correct string.")
            void toString_singleton_correctString() {
                assertEquals("C <-- front", classUnderTest.toString());
            }

            @Nested
            @TestInstance(TestInstance.Lifecycle.PER_CLASS)
            class WhenMany {

                @BeforeEach
                void enqueueMoreElements() {
                    // Order should be A, B, C, D, E
                    classUnderTest.enqueue("A", 0);
                    classUnderTest.enqueue("E", 20);
                    classUnderTest.enqueue("B", 5);
                    classUnderTest.enqueue("D", 15);
                    preState.enqueue("A", 0);
                    preState.enqueue("E", 20);
                    preState.enqueue("B", 5);
                    preState.enqueue("D", 15);
                }

                @Test
                @DisplayName("The Queue isn't empty.")
                void isEmpty_many_returnsFalse() {
                    assertFalse(classUnderTest.isEmpty());
                }

                @Test
                @DisplayName("The Queue has a size of 5.")
                void size_many_returnsFive() {
                    assertEquals(5, classUnderTest.size());
                }

                @Test
                @DisplayName("Enqueueing a lower priority element updates front.")
                void enqueue_lowerPriority_updatesFront() {
                    classUnderTest.enqueue("J", -1);
                    assertEquals("J", classUnderTest.first());
                }

                @Test
                @DisplayName("Enqueueing a higher priority element does not change the front.")
                void enqueue_higherPriority_unchangedFront() {
                    classUnderTest.enqueue("J", 1);
                    assertEquals("A", classUnderTest.first());
                }

                @Test
                @DisplayName("First on many Queue returns lowest priority data.")
                void first_many_returnsLowestPriorityData() {
                    assertEquals("A", classUnderTest.first());
                }

                @Test
                @DisplayName("First on many Queue doesn't change the queue.")
                void first_many_unchanged() {
                    classUnderTest.first();
                    assertEquals(preState, classUnderTest);
                }

                @Test
                @DisplayName("Dequeuing elements from a Queue does so in the prioritized order.")
                void dequeue_many_returnsElementsInCorrectSequence() {
                    assertEquals("A", classUnderTest.dequeue());
                    assertEquals("B", classUnderTest.dequeue());
                    assertEquals("C", classUnderTest.dequeue());
                    assertEquals("D", classUnderTest.dequeue());
                    assertEquals("E", classUnderTest.dequeue());
                }

                @Test
                @DisplayName("Dequeueing from many Queue changes Queue.")
                void dequeue_many_changedPriorityQueue() {
                    classUnderTest.dequeue();
                    assertEquals("B", classUnderTest.first());
                }

                @Test
                @DisplayName("Many Queue toString returns correct string.")
                void toString_many_correctString() {
                    assertEquals("A <-- front" + "\n" + "B" + "\n" + "C" + "\n" + "D" + "\n" + "E", classUnderTest.toString());
                }
            }

            @Nested
            @TestInstance(TestInstance.Lifecycle.PER_CLASS)
            class WhenDuplicatePriorities {

                @BeforeEach
                void enqueueMoreElements() {
                    // Order should be one of:
                    //      A, B, C, D, E, F
                    //      A, B, C, E, D, F
                    //      A, B, D, C, E, F
                    //      A, B, D, E, C, F
                    //      A, B, E, C, D, F
                    //      A, B, E, D, C, F
                    classUnderTest.enqueue("A", 0);
                    classUnderTest.enqueue("D", 10);
                    classUnderTest.enqueue("B", 5);
                    classUnderTest.enqueue("E", 10);
                    classUnderTest.enqueue("F", 15);
                    preState.enqueue("A", 0);
                    preState.enqueue("D", 10);
                    preState.enqueue("B", 5);
                    preState.enqueue("E", 10);
                    preState.enqueue("F", 15);
                }

                @Test
                @DisplayName("Queue with duplicate priorities still returns correct size.")
                void size_duplicates_returnsSix() {
                    assertEquals(6, classUnderTest.size());
                }

                @Test
                @DisplayName("Dequeueing from a Queue with duplicate priorities does so in correct order.")
                void dequeue_duplicates_returnsElementsInCorrectSequence() {
                    // my order: A, B, E, D, C, F
                    assertEquals("A", classUnderTest.dequeue());
                    assertEquals("B", classUnderTest.dequeue());
                    assertEquals("E", classUnderTest.dequeue());
                    assertEquals("D", classUnderTest.dequeue());
                    assertEquals("C", classUnderTest.dequeue());
                    assertEquals("F", classUnderTest.dequeue());
                }
            }
        }
    }
}

