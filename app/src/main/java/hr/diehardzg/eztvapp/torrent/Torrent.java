package hr.diehardzg.eztvapp.torrent;

import android.os.Parcel;
import android.os.Parcelable;

public class Torrent implements Parcelable
{
    private String name;
    private String magnet;
    private String torrent;
    private String size;
    private String seederi;
    private String relesed;

    public Torrent(String name, String magnet, String torrent, String size, String seederi, String relesed)
    {
        super();
        this.name = name;
        this.magnet = magnet;
        this.torrent = torrent;
        this.size = size;
        this.seederi = seederi;
        this.relesed = relesed;
    }

    public Torrent()
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

    public String getMagnet()
    {
        return magnet;
    }

    public void setMagnet(String magnet)
    {
        this.magnet = magnet;
    }

    public String getTorrent()
    {
        return torrent;
    }

    public void setTorrent(String torrent)
    {
        this.torrent = torrent;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public String getSeederi()
    {
        return seederi;
    }

    public void setSeederi(String seederi)
    {
        this.seederi = seederi;
    }

    public String getRelesed()
    {
        return relesed;
    }

    public void setRelesed(String relesed)
    {
        this.relesed = relesed;
    }

    @Override
    public String toString()
    {
        return "Torrent [\nname=" + name + ", \nmagnet=" + magnet + ", \ntorrent=" + torrent + ", \nsize=" + size + ", \nseederi=" + seederi + ", \nrelesed=" + relesed + "]";
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
        dest.writeString(this.magnet);
        dest.writeString(this.torrent);
        dest.writeString(this.size);
        dest.writeString(this.seederi);
        dest.writeString(this.relesed);
    }

    protected Torrent(Parcel in)
    {
        this.name = in.readString();
        this.magnet = in.readString();
        this.torrent = in.readString();
        this.size = in.readString();
        this.seederi = in.readString();
        this.relesed = in.readString();
    }

    public static final Creator<Torrent> CREATOR = new Creator<Torrent>()
    {
        @Override
        public Torrent createFromParcel(Parcel source)
        {
            return new Torrent(source);
        }

        @Override
        public Torrent[] newArray(int size)
        {
            return new Torrent[size];
        }
    };
}
