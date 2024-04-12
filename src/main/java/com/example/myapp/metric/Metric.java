package com.example.myapp.metric;


import com.example.myapp.entity.CKNumber;
import com.example.myapp.entity.CKReport;
import org.eclipse.jdt.core.dom.CompilationUnit;

public interface Metric {

	void execute(CompilationUnit cu, CKNumber result, CKReport report);
	void setResult(CKNumber result);
}
