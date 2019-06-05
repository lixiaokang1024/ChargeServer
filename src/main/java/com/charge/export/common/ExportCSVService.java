package com.charge.export.common;

import java.io.File;
import java.io.IOException;

public interface ExportCSVService {

    File exportCSVFile(String templateFilePath) throws IOException;

}
