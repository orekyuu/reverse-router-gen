package net.orekyuu.reverserouter.analyzed;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AnalyzedClasses {
    final Set<AnalyzedClass> classes;

    public AnalyzedClasses(Set<AnalyzedClass> classes) {
        this.classes = classes;
    }

    public AnalyzedClasses() {
        this(new HashSet<>());
    }

    public void add(AnalyzedClass analyzedClass) {
        classes.add(analyzedClass);
    }

    public Set<AnalyzedClass> all() {
        return Collections.unmodifiableSet(classes);
    }

    public AnalyzedClasses merge(AnalyzedClasses classes) {
        HashSet<AnalyzedClass> analyzedClasses = new HashSet<>(this.classes);
        analyzedClasses.addAll(classes.classes);
        return new AnalyzedClasses(analyzedClasses);
    }
}
