package hr.diehardzg.eztvapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import hr.diehardzg.eztvapp.serija.DetaljiSerije;

public class SerijaDetailsActivity extends Activity
{
    private static final String TAG = "SerijaDetailsActivity";

    TextView serijaNaziv;
    TextView serijaAirTime;
    TextView serijaOpis;
    TextView serijaPopis;
    ProgressBar progressBar;

    String link;
    GetHTMLdata getData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serija_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        link = "https://eztv.ag" + intent.getStringExtra("serijaLink");

        progressBar = (ProgressBar) findViewById(R.id.progresBAR);

        serijaNaziv = (TextView) findViewById(R.id.serijaNaziv);
        serijaAirTime = (TextView) findViewById(R.id.serijaAirTime);
        serijaOpis = (TextView) findViewById(R.id.serijaOpis);
        serijaPopis = (TextView) findViewById(R.id.serijaPopis);

        getData = new GetHTMLdata();
        getData.execute(link);
    }

    private class GetHTMLdata extends AsyncTask<String, Void, DetaljiSerije>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute");
            progressBar.setVisibility(View.VISIBLE);

            serijaNaziv.setVisibility(View.INVISIBLE);
            serijaAirTime.setVisibility(View.INVISIBLE);
            serijaOpis.setVisibility(View.INVISIBLE);
            serijaPopis.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(DetaljiSerije detaljiSerije)
        {
            super.onPostExecute(detaljiSerije);

            if(detaljiSerije == null)
            {
                Toast.makeText(SerijaDetailsActivity.this, "EZTV not responding, please try again", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
            else
            {
                serijaNaziv.setText(detaljiSerije.getNazivSerije());
                serijaAirTime.setText(detaljiSerije.getAirTime());
                serijaOpis.setText(detaljiSerije.getOpis());
                serijaPopis.setText(Html.fromHtml(detaljiSerije.getPopisEpizoda()));

                serijaNaziv.setVisibility(View.VISIBLE);
                serijaAirTime.setVisibility(View.VISIBLE);
                serijaOpis.setVisibility(View.VISIBLE);
                serijaPopis.setVisibility(View.VISIBLE);

                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        protected DetaljiSerije doInBackground(String... params)
        {
            return getHTMLdata(params[0]);
        }
    }

    private DetaljiSerije getHTMLdata(String link)
    {
        DetaljiSerije serija = new DetaljiSerije();
        try
        {
            Document doc = Jsoup.connect(link).get();

            Elements tablica = doc.select("div[itemtype='http://schema.org/TVSeries'] table.forum_header_border_normal tbody");

            Element tbody = tablica.get(0);
            Elements rows = tbody.select("tr");

            Element row = rows.get(0);
            Elements tds = row.select("td");

            serija.setNazivSerije(tds.text());

            Element row2 = rows.get(1);
            Elements centers = row2.select("td center");

            Element centar = centers.get(0);
            Elements triTablice = centar.select("table");

            Element jednaTablica = triTablice.get(0);
            serija.setOpis(jednaTablica.select("tr:nth-child(2) td").text());
            serija.setAirTime(jednaTablica.select("tr:nth-child(5) td div:first-child").text());

            jednaTablica = triTablice.get(1);

            Elements noviTDovi = jednaTablica.select("tbody tr:nth-child(2) td");
            Element praviTD = noviTDovi.get(0);

            serija.setPopisEpizoda(praviTD.select("div").html());
        }
        catch (IOException e)
        {
            Log.e(TAG, "getHTMLdata: " + e.getMessage(), e);
            return null;
        }

        return serija;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.serija_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_refresh:
                getData = new GetHTMLdata();
                getData.execute(link);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
