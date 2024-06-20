package org.kviat;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

import com.google.auto.service.AutoService;

@SupportedAnnotationTypes("org.kviat.GenerateClass")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class GenerateClassProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing annotations...");
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateClass.class)) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Found @GenerateClass annotation", element);
            if (element.getKind() != ElementKind.CLASS) {
                error("Only classes can be annotated with @GenerateClass", element);
                return true;
            }

            TypeElement classElement = (TypeElement) element;
            GenerateClass generateClassAnnotation = classElement.getAnnotation(GenerateClass.class);
            String className = generateClassAnnotation.className();
            String packageName = generateClassAnnotation.packageName();
            String[] fields = generateClassAnnotation.fields();

            try {
                generateClass(packageName, className, fields);
            } catch (IOException e) {
                error("Failed to generate class: " + e.getMessage(), element);
            }
        }
        return true;
    }

    private void generateClass(String packageName, String className, String[] fields) throws IOException {
        String fqClassName = packageName + "." + className;
        Writer writer = processingEnv.getFiler().createSourceFile(fqClassName).openWriter();

        // Generate package declaration
        writer.write("package " + packageName + ";\n\n");

        // Generate class declaration
        writer.write("public class " + className + " {\n\n");

        // Generate fields
        for (String field : fields) {
            writer.write("    private " + field + ";\n");
        }
        writer.write("\n");

        // Generate default constructor
        writer.write("    public " + className + "() { }\n\n");

        // Generate getters and setters
        for (String field : fields) {
            String fieldName = field.substring(field.lastIndexOf(" ") + 1);
            String typeName = field.substring(0, field.lastIndexOf(" "));
            writer.write("    public " + typeName + " get" + capitalize(fieldName) + "() {\n");
            writer.write("        return " + fieldName + ";\n");
            writer.write("    }\n\n");
            writer.write("    public void set" + capitalize(fieldName) + "(" + typeName + " " + fieldName + ") {\n");
            writer.write("        this." + fieldName + " = " + fieldName + ";\n");
            writer.write("    }\n\n");
        }

        // Close class
        writer.write("}\n");
        writer.close();
    }

    private void error(String message, Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}

