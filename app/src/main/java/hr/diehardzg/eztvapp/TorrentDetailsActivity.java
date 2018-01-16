package hr.diehardzg.eztvapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import hr.diehardzg.eztvapp.torrent.Torrent;

public class TorrentDetailsActivity extends Activity
{
    TextView name;
    TextView magnet;
    TextView torrent;
    TextView size;
    TextView relesed;
    TextView seeds;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Torrent tor = intent.getParcelableExtra("torrent");

        name = (TextView) findViewById(R.id.torrentName);
        magnet = (TextView) findViewById(R.id.torrentMagnet);
        torrent = (TextView) findViewById(R.id.torrentTorrent);
        size = (TextView) findViewById(R.id.size);
        relesed = (TextView) findViewById(R.id.relesed);
        seeds = (TextView) findViewById(R.id.seederi);

        name.setText(tor.getName());
        magnet.setText(tor.getMagnet());
        torrent.setText(tor.getTorrent());
        size.setText("Size: " + tor.getSize());
        relesed.setText("Relesed: " + tor.getRelesed());
        seeds.setText("Seeds: " + tor.getSeederi());
    }
}
