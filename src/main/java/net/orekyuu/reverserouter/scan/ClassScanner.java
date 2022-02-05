package net.orekyuu.reverserouter.scan;

import net.orekyuu.reverserouter.analyzed.AnalyzedClasses;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementScanner14;

public class ClassScanner extends ElementScanner14<AnalyzedClasses, AnalyzedClasses> {

    private final ClassStruct struct = new ClassStruct();

    @Override
    public AnalyzedClasses visitType(TypeElement e, AnalyzedClasses classes) {
        struct.currentType(e);
        super.visitType(e, classes);
        classes.add(struct.createAnalyzed());
        struct.clear();
        return classes;
    }

    @Override
    public AnalyzedClasses visitExecutable(ExecutableElement e, AnalyzedClasses analyzedClasses) {
        struct.addMethod(e);
        return super.visitExecutable(e, analyzedClasses);
    }
}
