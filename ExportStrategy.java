package com.example.adminpanel.factory;

import java.util.List;

public interface ExportStrategy<T> {
    byte[] export(List<T> items);
    String getFormat();
}