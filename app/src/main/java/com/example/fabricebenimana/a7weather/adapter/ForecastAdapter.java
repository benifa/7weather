package com.example.fabricebenimana.a7weather.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fabricebenimana.a7weather.R;
import com.example.fabricebenimana.a7weather.utils.ForecastViewHelper;
import com.example.fabricebenimana.a7weather.models.ListItem;
import com.example.fabricebenimana.a7weather.models.Main;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    List<ListItem> mForecasts;

    public ForecastAdapter(List mForecasts) {
        this.mForecasts = mForecasts;
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.forcast_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder holder, int position) {
        final ListItem forecast = mForecasts.get(position);
        final Main main = forecast.getMain();
        holder.mMinTemp.setText(String.format(holder.mMinTempString, main.getTempMin()));
        holder.mMaxTemp.setText(String.format(holder.mMaxTempString, main.getTempMax()));
        holder.mHumidity.setText(String.format(holder.mHumidityFormatter, main.getHumidity()));
        holder.mWindSpeed.setText(String.format(holder.mWindFormatter, forecast.getWind().getSpeed()));
        holder.mDate.setText(forecast.getDtTxt());
        final String status = ForecastViewHelper.buildWeatherStatus(holder.itemView.getResources(), forecast);
        holder.mStatus.setText(status);
    }


    @Override
    public int getItemCount() {
        return mForecasts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.status)
        protected TextView mStatus;
        @BindView(R.id.date)
        protected TextView mDate;
        @BindView(R.id.min_temp)
        protected IconTextView mMinTemp;
        @BindView(R.id.max_temp)
        protected IconTextView mMaxTemp;
        @BindView(R.id.humidity)
        protected TextView mHumidity;
        @BindView(R.id.wind_speed)
        protected TextView mWindSpeed;
        @BindString(R.string.temperature_humidity)
        protected String mHumidityFormatter;
        @BindString(R.string.temperature_wind)
        protected String mWindFormatter;
        @BindString(R.string.min_temp_formatter)
        protected String mMinTempString;
        @BindString(R.string.max_temp_formatter)
        protected String mMaxTempString;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
