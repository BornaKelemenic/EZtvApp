package hr.diehardzg.eztvapp.serija;

import android.os.Parcel;
import android.os.Parcelable;

public class DetaljiSerije implements Parcelable
{
    private String nazivSerije;
    private String opis;
    private String airTime;
    private String popisEpizoda;

    public DetaljiSerije()
    {
    }

    public DetaljiSerije(String nazivSerije, String opis, String airTime, String popisEpizoda)
    {
        this.nazivSerije = nazivSerije;
        this.opis = opis;
        this.airTime = airTime;
        this.popisEpizoda = popisEpizoda;
    }

    public String getNazivSerije()
    {
        return nazivSerije;
    }

    public void setNazivSerije(String nazivSerije)
    {
        this.nazivSerije = nazivSerije;
    }

    public String getOpis()
    {
        return opis;
    }

    public void setOpis(String opis)
    {
        this.opis = opis;
    }

    public String getAirTime()
    {
        return airTime;
    }

    public void setAirTime(String airTime)
    {
        this.airTime = airTime;
    }

    public String getPopisEpizoda()
    {
        return popisEpizoda;
    }

    public void setPopisEpizoda(String popisEpizoda)
    {
        this.popisEpizoda = popisEpizoda;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.nazivSerije);
        dest.writeString(this.opis);
        dest.writeString(this.airTime);
        dest.writeString(this.popisEpizoda);
    }

    protected DetaljiSerije(Parcel in)
    {
        this.nazivSerije = in.readString();
        this.opis = in.readString();
        this.airTime = in.readString();
        this.popisEpizoda = in.readString();
    }

    public static final Creator<DetaljiSerije> CREATOR = new Creator<DetaljiSerije>()
    {
        @Override
        public DetaljiSerije createFromParcel(Parcel source)
        {
            return new DetaljiSerije(source);
        }

        @Override
        public DetaljiSerije[] newArray(int size)
        {
            return new DetaljiSerije[size];
        }
    };
}