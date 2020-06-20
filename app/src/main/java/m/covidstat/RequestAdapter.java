package m.covidstat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RequestAdapter  extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private List<Volunteer> volunteer;
    private onPhoneClick onPhoneClick;

    public RequestAdapter(List<Volunteer> volunteer) {
        this.volunteer = volunteer;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_view, parent, false);
        return new RequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.name.setText(holder.itemView.getResources().getString(R.string.name,volunteer.get(position).getName()));
        holder.location.setText(volunteer.get(position).getLocation());
        holder.phone.setText(holder.itemView.getResources().getString(R.string.phone,volunteer.get(position).getPhone()));
        holder.description.setText(volunteer.get(position).getDescription());
        holder.phoneImg.setOnClickListener(v -> onPhoneClick.onPhoneClick(volunteer.get(position).getPhone()));

    }

    public void setOnPhoneClickListener(onPhoneClick onPhoneClickListener){
        this.onPhoneClick = onPhoneClickListener;
    }

    @Override
    public int getItemCount() {
        return volunteer.size();
    }

    public void updateAdapter(List<Volunteer> volunteers){
        volunteer = volunteers;
        notifyDataSetChanged();
    }


    public class RequestViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView location,name, phone , description;
        AppCompatImageView phoneImg;


        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            phone = itemView.findViewById(R.id.phone);
            description = itemView.findViewById(R.id.description);
            phoneImg = itemView.findViewById(R.id.phone_icon);
        }
    }

    public interface onPhoneClick{
        void onPhoneClick(String phone);
    }
}
