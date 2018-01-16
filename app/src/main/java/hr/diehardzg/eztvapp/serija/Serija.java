package hr.diehardzg.eztvapp.serija;

import android.os.Parcel;
import android.os.Parcelable;

public class Serija implements Parcelable
{
    private String name;
    private String link;
    private String status;
    private String rating;

    public Serija(String name, String link, String status, String rating)
    {
        this.name = name;
        this.link = link;
        this.status = status;
        this.rating = rating;
    }

    public Serija()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    @Override
    public String toString()
    {
        return "Serija{\n" + "name='" + name + '\'' + ", \nlink='" + link + '\'' + ", \nstatus='" + status + '\'' + ", \nrating='" + rating + '\'' + '}';
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.name);
        dest.writeString(this.link);
        dest.writeString(this.status);
        dest.writeString(this.rating);
    }

    protected Serija(Parcel in)
    {
        this.name = in.readString();
        this.link = in.readString();
        this.status = in.readString();
        this.rating = in.readString();
    }

    public static final Creator<Serija> CREATOR = new Creator<Serija>()
    {
        @Override
        public Serija createFromParcel(Parcel source)
        {
            return new Serija(source);
        }

        @Override
        public Serija[] newArray(int size)
        {
            return new Serija[size];
        }
    };
}
