package fr.axonic.avek.gui.view;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.axonic.avek.gui.model.results.AState;
import fr.axonic.avek.gui.model.results.State;

import java.io.IOException;

/**
 * Created by cduffau on 12/07/16.
 */
public class EnumAdapter<T extends Enum<T>> extends TypeAdapter<T>{

    private final Class<T> tclass;

    public EnumAdapter(Class<T> tClass){
        this.tclass=tClass;
    }

    @Override
    public void write(JsonWriter jsonWriter, T aState) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name").value(aState.name());
        jsonWriter.endObject();
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        T res=T.valueOf(tclass,jsonReader.nextString());
        jsonReader.endObject();
        return res;
    }
}
