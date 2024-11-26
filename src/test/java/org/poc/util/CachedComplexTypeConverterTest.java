package org.poc.util;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.poc.annotation.AttributeMapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("UnitTest")
class CachedComplexTypeConverterTest {

    // Test classes
    public static class ClassA {
        private String commonField;
        @AttributeMapping(targetAttribute = "mappedFieldB", targetClass = ClassB.class)
        @AttributeMapping(targetAttribute = "mappedFieldC", targetClass = ClassC.class)
        private String annotatedField;

        public ClassA(String commonField, String annotatedField) {
            this.commonField = commonField;
            this.annotatedField = annotatedField;
        }

        public String getCommonField() {
            return commonField;
        }

        public String getAnnotatedField() {
            return annotatedField;
        }
    }

    public static class ClassB {
        private String commonField;
        private String mappedFieldB;

        public String getCommonField() {
            return commonField;
        }

        public String getMappedFieldB() {
            return mappedFieldB;
        }
    }

    public static class ClassC {
        private String mappedFieldC;

        public String getMappedFieldC() {
            return mappedFieldC;
        }
    }

    public static class ClassD {
        private String unrelatedField;

        public String getUnrelatedField() {
            return unrelatedField;
        }
    }

    // Test methods
    @Test
    public void testConvertClassAToClassBWithCommonField() {
        ClassA classA = new ClassA("commonValue", "annotatedValue");
        ClassB classB = CachedComplexTypeConverter.convert(classA, ClassB.class);

        assertEquals(classA.getCommonField(), classB.getCommonField());
    }

    @Test
    public void testConvertClassAToClassBWithAnnotatedField() {
        ClassA classA = new ClassA("commonValue", "annotatedValue");
        ClassB classB = CachedComplexTypeConverter.convert(classA, ClassB.class);

        assertEquals(classA.getAnnotatedField(), classB.getMappedFieldB());
    }

    @Test
    public void testConvertClassAToClassBAndClassCWithMultipleAnnotations() {
        ClassA classA = new ClassA("commonValue", "annotatedValue");
        ClassB classB = CachedComplexTypeConverter.convert(classA, ClassB.class);
        ClassC classC = CachedComplexTypeConverter.convert(classA, ClassC.class);

        assertEquals(classA.getAnnotatedField(), classB.getMappedFieldB());
        assertEquals(classA.getAnnotatedField(), classC.getMappedFieldC());
    }

    @Test
    public void testConvertClassAToClassDWithNoMatchingFields() {
        ClassA classA = new ClassA("commonValue", "annotatedValue");
        ClassD classD = CachedComplexTypeConverter.convert(classA, ClassD.class);

        assertNull(classD.getUnrelatedField());
    }
}