/*
 * Copyright (c) UICHUIMI 2016
 *
 * This file is part of VariantCallFormat.
 *
 * VariantCallFormat is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * VariantCallFormat is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Foobar.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package vcf.combine;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by uichuimi on 24/05/16.
 */
public class Sample {

    private final File file;
    private final String name;
    private Property<Status> status = new SimpleObjectProperty<>(Status.AFFECTED);
    private File mistFile;
    private long size;
    private Mist mist;

    public Sample(File file, String name, long size) {
        this.file = file;
        this.name = name;
        this.size = size;
    }

    public Sample(File file, String name) {
        this.file = file;
        this.name = name;
        this.size = getLinesCount();
    }

    private long getLinesCount() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines().filter(line -> !line.startsWith("#")).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public Property<Status> statusProperty() {
        return status;
    }

    public File getFile() {
        return file;
    }

    public Status getStatus() {
        return status.getValue();
    }

    public void setStatus(Status status) {
        this.status.setValue(status);
    }

    public File getMistFile() {
        return mistFile;
    }

    public void setMistFile(File mistFile) {
        this.mistFile = mistFile;
        mist = MistFactory.load(mistFile);
    }

    public Mist getMist() {
        return mist;
    }

    public long getSize() {
        return size;
    }

    public enum Status {
        WILD, AFFECTED, HOMOZYGOUS, HETEROZYGOUS
    }
}
