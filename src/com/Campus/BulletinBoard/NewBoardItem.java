package com.Campus.BulletinBoard;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.Toast;

import com.Campus.R;
import com.Campus.Utility.BitmapHandler;

import eu.janmuller.android.simplecropimage.CropImage;

public class NewBoardItem extends FragmentActivity {
	
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransaction;
	public SharedPreferences Settings;
	private NewDigiPostEnterInfoFragment New;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_digipost_activity);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Settings = getSharedPreferences("Settings", 0);
		mFragmentManager = getSupportFragmentManager();
		mFragmentTransaction = mFragmentManager.beginTransaction();
		mFragmentTransaction.replace(R.id.NewDigiPostContainer, new NewDigiPostInitFragment());
		mFragmentTransaction.commit();
	}
	
	public void setNew(NewDigiPostEnterInfoFragment N){
		New = N;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_digi_post, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     if (requestCode == 1 && resultCode == RESULT_OK && data!=null){
	    	 Uri SelectedImage = data.getData();
	    	 String[] filePathColumn = { MediaStore.Images.Media.DATA };
	    	 Cursor cursor = getContentResolver().query(SelectedImage, filePathColumn, null, null, null);
	    	 cursor.moveToFirst();
	    	 runCropImage(cursor.getString(cursor.getColumnIndex(filePathColumn[0])));
	    	 
	     }
	     else if(requestCode == 500 && resultCode == RESULT_OK){
	    	 String path = data.getStringExtra(CropImage.IMAGE_PATH);
	    	 New.onActivityResult(path);
	     }
	 }
	 
	 private void runCropImage(String path){
		 Intent intent = new Intent(this, CropImage.class);
		 intent.putExtra(CropImage.IMAGE_PATH, path);
		 intent.putExtra(CropImage.SCALE, true);
		 intent.putExtra(CropImage.ASPECT_X, 3);
		 intent.putExtra(CropImage.ASPECT_Y, 3);
		 startActivityForResult(intent, 500);
	 }
	 
public static class NewDigiPostInitFragment extends Fragment {
			
			private Button NewDigiPostSellStuffBtn;
			private Button NewDigiPostWantStuffBtn;
			private Button NewDigiPostEventBtn;
			private Button NewDigiPostHousingBtn;
			private Button NewDigiPostTutoringBtn;
			private Button NewDigiPostOddJobsBtn;
			private Button NewDigiPostCarpoolingBtn;
			private TableRow SellTR;
			private TableRow WantTR;
			private TableRow EventTR;
			private TableRow HousingTR;
			private TableRow TutoringTR;
			private TableRow OddJobsTR;
			private TableRow CarpoolingTR;
			private FragmentManager mFragmentManager;
			private FragmentTransaction mFragmentTransaction;
			private Fragment mFragment;
			private final Bundle args = new Bundle();
			
			@Override
			public View onCreateView(LayoutInflater Inflater, ViewGroup Container, Bundle SavedInstanceState){
				View v = Inflater.inflate(R.layout.new_digipost_init, Container, false);
				NewDigiPostSellStuffBtn = (Button) v.findViewById(R.id.NewDigiPostSellStuffBtn);
				NewDigiPostWantStuffBtn = (Button) v.findViewById(R.id.NewDigiPostWantStuffBtn);
				NewDigiPostEventBtn = (Button) v.findViewById(R.id.NewDigiPostEventBtn);
				NewDigiPostHousingBtn = (Button) v.findViewById(R.id.NewDigiPostHousingBtn);
				NewDigiPostTutoringBtn = (Button) v.findViewById(R.id.NewDigiPostTutoringBtn);
				NewDigiPostOddJobsBtn = (Button) v.findViewById(R.id.NewDigiPostOddJobsBtn);
				NewDigiPostCarpoolingBtn = (Button) v.findViewById(R.id.NewDigiPostCarPoolingBtn);
				
				SellTR = (TableRow) v.findViewById(R.id.NewDigiPostSellStuffTR);
				WantTR = (TableRow) v.findViewById(R.id.NewDigiPostWantStuffTR);
				EventTR = (TableRow) v.findViewById(R.id.NewDigiPostEventTR);
				HousingTR = (TableRow) v.findViewById(R.id.NewDigiPostHousingTR);
				TutoringTR = (TableRow) v.findViewById(R.id.NewDigiPostTutoringTR);
				OddJobsTR = (TableRow) v.findViewById(R.id.NewDigiPostOddJobsTR);
				CarpoolingTR = (TableRow) v.findViewById(R.id.NewDigiPostCarPoolingTR);
				
				NewDigiPostSellStuffBtn.setOnClickListener(NewDigiPostBtnListener);
				NewDigiPostWantStuffBtn.setOnClickListener(NewDigiPostBtnListener);
				NewDigiPostEventBtn.setOnClickListener(NewDigiPostBtnListener);
				NewDigiPostHousingBtn.setOnClickListener(NewDigiPostBtnListener);
				NewDigiPostTutoringBtn.setOnClickListener(NewDigiPostBtnListener);
				NewDigiPostOddJobsBtn.setOnClickListener(NewDigiPostBtnListener);
				NewDigiPostCarpoolingBtn.setOnClickListener(NewDigiPostBtnListener);
				
				mFragmentManager = getFragmentManager();
				mFragment = new NewDigiPostEnterInfoFragment();
				
				return v;
			}
			
			private OnClickListener NewDigiPostBtnListener = new OnClickListener(){
				@Override
				public void onClick(View v) {
					int ID = v.getId();
					switch(ID){
					case R.id.NewDigiPostSellStuffBtn:
						SellTR.setSelected(true);
						args.putInt("DigiPostType", 0);
						mFragment.setArguments(args);
						mFragmentTransaction = mFragmentManager.beginTransaction();
						mFragmentTransaction.setCustomAnimations(R.anim.view_slide_in, R.anim.view_slide_out, R.anim.view_slide_in, R.anim.view_slide_out);
						mFragmentTransaction.replace(R.id.NewDigiPostContainer, mFragment);
						mFragmentTransaction.addToBackStack(null);
						mFragmentTransaction.commit();
						break;
					case R.id.NewDigiPostWantStuffBtn:
						WantTR.setSelected(true);
						args.putInt("DigiPostType", 1);
						mFragment.setArguments(args);
						mFragmentTransaction = mFragmentManager.beginTransaction();
						mFragmentTransaction.setCustomAnimations(R.anim.view_slide_in, R.anim.view_slide_out, R.anim.view_slide_in, R.anim.view_slide_out);
						mFragmentTransaction.replace(R.id.NewDigiPostContainer, mFragment);
						mFragmentTransaction.addToBackStack(null);
						mFragmentTransaction.commit();
						break;
					case R.id.NewDigiPostEventBtn:
						EventTR.setSelected(true);
						args.putInt("DigiPostType", 2);
						mFragment.setArguments(args);
						mFragmentTransaction = mFragmentManager.beginTransaction();
						mFragmentTransaction.setCustomAnimations(R.anim.view_slide_in, R.anim.view_slide_out, R.anim.view_slide_in, R.anim.view_slide_out);
						mFragmentTransaction.replace(R.id.NewDigiPostContainer, mFragment);
						mFragmentTransaction.addToBackStack(null);
						mFragmentTransaction.commit();
						break;
					case R.id.NewDigiPostHousingBtn:
						HousingTR.setSelected(true);
						args.putInt("DigiPostType", 3);
						mFragment.setArguments(args);
						mFragmentTransaction = mFragmentManager.beginTransaction();
						mFragmentTransaction.setCustomAnimations(R.anim.view_slide_in, R.anim.view_slide_out, R.anim.view_slide_in, R.anim.view_slide_out);
						mFragmentTransaction.replace(R.id.NewDigiPostContainer, mFragment);
						mFragmentTransaction.addToBackStack(null);
						mFragmentTransaction.commit();
						break;
					case R.id.NewDigiPostTutoringBtn:
						TutoringTR.setSelected(true);
						args.putInt("DigiPostType", 4);
						mFragment.setArguments(args);
						mFragmentTransaction = mFragmentManager.beginTransaction();
						mFragmentTransaction.setCustomAnimations(R.anim.view_slide_in, R.anim.view_slide_out, R.anim.view_slide_in, R.anim.view_slide_out);
						mFragmentTransaction.replace(R.id.NewDigiPostContainer, mFragment);
						mFragmentTransaction.addToBackStack(null);
						mFragmentTransaction.commit();
						break;
					case R.id.NewDigiPostOddJobsBtn:
						OddJobsTR.setSelected(true);
						args.putInt("DigiPostType", 5);
						mFragment.setArguments(args);
						mFragmentTransaction = mFragmentManager.beginTransaction();
						mFragmentTransaction.setCustomAnimations(R.anim.view_slide_in, R.anim.view_slide_out, R.anim.view_slide_in, R.anim.view_slide_out);
						mFragmentTransaction.replace(R.id.NewDigiPostContainer, mFragment);
						mFragmentTransaction.addToBackStack(null);
						mFragmentTransaction.commit();
						break;
					case R.id.NewDigiPostCarPoolingBtn:
						CarpoolingTR.setSelected(true);
						args.putInt("DigiPostType", 6);
						mFragment.setArguments(args);
						mFragmentTransaction = mFragmentManager.beginTransaction();
						mFragmentTransaction.setCustomAnimations(R.anim.view_slide_in, R.anim.view_slide_out, R.anim.view_slide_in, R.anim.view_slide_out);
						mFragmentTransaction.replace(R.id.NewDigiPostContainer, mFragment);
						mFragmentTransaction.addToBackStack(null);
						mFragmentTransaction.commit();
						break;
					}
				}
			};
		}

 public static class NewDigiPostEnterInfoFragment extends Fragment implements GeneralCallback {
	
	private EditText EnterTitle;
	private EditText EnterPrice;
	private EditText EnterDescription;
	private EditText EnterDate;
	private EditText EnterTime;
	private EditText EnterMaximumOccupancy;
	private EditText EnterSubject;
	private EditText EnterLocationOfOrigin;
	private EditText EnterDestination;
	private EditText[] Fields;
	private TableRow EnterTitleTR;
	private TableRow EnterPriceTR;
	private TableRow EnterDescriptionTR;
	private TableRow EnterDateTR;
	private TableRow EnterTimeTR;
	private TableRow EnterMaximumOccupancyTR;
	private TableRow EnterSubjectTR;
	private TableRow EnterLocationOfOriginTR;
	private TableRow EnterDestinationTR;
	private LinearLayout FullLayout;
	private ProgressBar Loading;
	private AddImage addImage;
	private ImageButton Image1;
	private ImageButton Image2;
	private ImageButton Image3;
	private ImageButton Image4;
	private ImageButton Image5;
	private ImageButton Image6;
	private String[] ImagesArr = new String[]{null, null, null, null, null, null};
	private int DigiPostType;
	private static Activity ParentActivity;
	private NewBoardItem Activity;
	private final HashMap<String, String> Info = new HashMap<String, String>();
	private Button SubmitButton;
	
	@Override
	public View onCreateView(LayoutInflater Inflater, ViewGroup Container, Bundle SavedInstanceState){
		View v = Inflater.inflate(R.layout.new_digi_post_enter_info, Container, false);
		Initialize(v);
		ParentActivity = getActivity();
		Activity = (NewBoardItem) ParentActivity;
		Activity.setNew(this);
		DigiPostType = getArguments().getInt("DigiPostType");
		switch(getArguments().getInt("DigiPostType")){
		case 0:
			EnterTitleTR.setVisibility(View.VISIBLE);
			EnterPriceTR.setVisibility(View.VISIBLE);
			EnterDescriptionTR.setVisibility(View.VISIBLE);
			Fields = new EditText[]{EnterTitle, EnterPrice, EnterDescription};
			break;
		case 1:
			EnterTitleTR.setVisibility(View.VISIBLE);
			EnterPriceTR.setVisibility(View.VISIBLE);
			EnterDescriptionTR.setVisibility(View.VISIBLE);
			Fields = new EditText[]{EnterTitle, EnterPrice, EnterDescription};
			break;
		case 2:
			EnterTitleTR.setVisibility(View.VISIBLE);
			EnterDateTR.setVisibility(View.VISIBLE);
			EnterTimeTR.setVisibility(View.VISIBLE);
			EnterPriceTR.setVisibility(View.VISIBLE);
			EnterMaximumOccupancyTR.setVisibility(View.VISIBLE);
			EnterDescriptionTR.setVisibility(View.VISIBLE);
			Fields = new EditText[]{EnterTitle, EnterPrice, EnterDescription, EnterDate, EnterTime, EnterMaximumOccupancy};
			break;
		case 3:
			EnterTitleTR.setVisibility(View.VISIBLE);
			EnterPriceTR.setVisibility(View.VISIBLE);
			EnterDescriptionTR.setVisibility(View.VISIBLE);
			Fields = new EditText[]{EnterTitle, EnterPrice, EnterDescription};
			break;
		case 4:
			EnterTitleTR.setVisibility(View.VISIBLE);
			EnterSubjectTR.setVisibility(View.VISIBLE);
			EnterPriceTR.setVisibility(View.VISIBLE);
			EnterDescriptionTR.setVisibility(View.VISIBLE);
			Fields = new EditText[]{EnterTitle, EnterPrice, EnterDescription, EnterSubject};
			break;
		case 5:
			EnterTitleTR.setVisibility(View.VISIBLE);
			EnterPriceTR.setVisibility(View.VISIBLE);
			EnterDescriptionTR.setVisibility(View.VISIBLE);
			Fields = new EditText[]{EnterTitle, EnterPrice, EnterDescription};
			break;
		case 6:
			EnterTitleTR.setVisibility(View.VISIBLE);
			EnterDescriptionTR.setVisibility(View.VISIBLE);
			EnterLocationOfOriginTR.setVisibility(View.VISIBLE);
			EnterDestinationTR.setVisibility(View.VISIBLE);
			EnterDateTR.setVisibility(View.VISIBLE);
			EnterPriceTR.setVisibility(View.VISIBLE);
			Fields = new EditText[]{EnterTitle, EnterPrice, EnterDescription, EnterLocationOfOrigin, EnterDate, EnterDestination};
			break;
		}	
		return v;
	}
	
	private void Initialize(View v){
		EnterTitle = (EditText) v.findViewById(R.id.NewDigiPostEnterTitle);
		EnterPrice = (EditText) v.findViewById(R.id.NewDigiPostEnterPrice);
		EnterDescription = (EditText) v.findViewById(R.id.NewDigiPostEnterDescription);
		EnterDate = (EditText) v.findViewById(R.id.NewDigiPostEnterDate);
		EnterTime = (EditText) v.findViewById(R.id.NewDigiPostEnterTime);
		EnterMaximumOccupancy = (EditText) v.findViewById(R.id.NewDigiPostEnterMaximumOccupancy);
		EnterSubject = (EditText) v.findViewById(R.id.NewDigiPostEnterSubject);
		EnterLocationOfOrigin = (EditText) v.findViewById(R.id.NewDigiPostEnterLocationOfOrigin);
		EnterDestination = (EditText) v.findViewById(R.id.NewDigiPostEnterDestination);
		FullLayout = (LinearLayout) v.findViewById(R.id.NewDigiPostEnterInfoLayout);
		Loading = (ProgressBar) v.findViewById(R.id.NewDigiPostEnterInfoLoading);
		
		EnterTitleTR = (TableRow) v.findViewById(R.id.TitleTR);
		EnterPriceTR = (TableRow) v.findViewById(R.id.PriceTR);
		EnterDescriptionTR = (TableRow) v.findViewById(R.id.DescriptionTR);
		EnterDateTR = (TableRow) v.findViewById(R.id.DateTR);
		EnterTimeTR = (TableRow) v.findViewById(R.id.TimeTR);
		EnterMaximumOccupancyTR = (TableRow) v.findViewById(R.id.MaximumOccupancyTR);
		EnterSubjectTR = (TableRow) v.findViewById(R.id.SubjectTR);
		EnterLocationOfOriginTR = (TableRow) v.findViewById(R.id.LocationOfOriginTR);
		EnterDestinationTR = (TableRow) v.findViewById(R.id.DestinationTR);
		
		SubmitButton = (Button) v.findViewById(R.id.NewDigiPostEnterInfoSubmitBtn);
		
		EnterTitleTR.setVisibility(View.GONE);
		EnterPriceTR.setVisibility(View.GONE);
		EnterDescriptionTR.setVisibility(View.GONE);
		EnterDateTR.setVisibility(View.GONE);
		EnterTimeTR.setVisibility(View.GONE);
		EnterMaximumOccupancyTR.setVisibility(View.GONE);
		EnterSubjectTR.setVisibility(View.GONE);
		EnterLocationOfOriginTR.setVisibility(View.GONE);
		EnterDestinationTR.setVisibility(View.GONE);
		
		
		Image1 = (ImageButton) v.findViewById(R.id.NewDigiPostIMG1);
		Image2 = (ImageButton) v.findViewById(R.id.NewDigiPostIMG2);
		Image3 = (ImageButton) v.findViewById(R.id.NewDigiPostIMG3);
		Image4 = (ImageButton) v.findViewById(R.id.NewDigiPostIMG4);
		Image5 = (ImageButton) v.findViewById(R.id.NewDigiPostIMG5);
		Image6 = (ImageButton) v.findViewById(R.id.NewDigiPostIMG6);
		
		Image1.setOnClickListener(ImageClickListener);
		Image2.setOnClickListener(ImageClickListener);
		Image3.setOnClickListener(ImageClickListener);
		Image4.setOnClickListener(ImageClickListener);
		Image5.setOnClickListener(ImageClickListener);
		Image6.setOnClickListener(ImageClickListener);
		
		SubmitButton.setOnClickListener(SubmitListener);
		
		EnterPrice.addTextChangedListener(mTextWatcher);
		
	}
	
	private OnClickListener SubmitListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			if (FieldsFilled()) {
			Info.put("User", Activity.Settings.getString("Username", "NULL"));
			switch(DigiPostType){
			case 0:
				Info.put("Title", EnterTitle.getText().toString());
				Info.put("Price", EnterPrice.getText().toString().replaceAll("\\$", ""));
				Info.put("Description", EnterDescription.getText().toString());
				Info.put("Type", "Sell");
				Info.put("DateStamp", new SimpleDateFormat("yyyy-MM-dd").format(new java.sql.Date(new java.util.Date().getTime())));
				break;
			case 1: 
				Info.put("Title", EnterTitle.getText().toString());
				Info.put("Price", EnterPrice.getText().toString());
				Info.put("Description", EnterDescription.getText().toString());
				Info.put("Type", "Want");
				Info.put("DateStamp", new SimpleDateFormat("yyyy-MM-dd").format(new java.sql.Date(new java.util.Date().getTime())));
				break;
			case 2:
				Info.put("Title", EnterTitle.getText().toString());
				Info.put("Price", EnterPrice.getText().toString());
				Info.put("Description", EnterDescription.getText().toString());
				Info.put("Time", EnterTime.getText().toString());
				Info.put("Date", EnterDate.getText().toString());
				Info.put("MaximumOccupancy", EnterMaximumOccupancy.getText().toString());
				Info.put("Type", "Event");
				Info.put("DateStamp", new SimpleDateFormat("yyyy-MM-dd").format(new java.sql.Date(new java.util.Date().getTime())));
				break;
			case 3:
				Info.put("Title", EnterTitle.getText().toString());
				Info.put("Price", EnterPrice.getText().toString());
				Info.put("Description", EnterDescription.getText().toString());
				Info.put("Type", "Housing");
				Info.put("DateStamp", new SimpleDateFormat("yyyy-MM-dd").format(new java.sql.Date(new java.util.Date().getTime())));
				break;
			case 4:
				Info.put("Title", EnterTitle.getText().toString());
				Info.put("Price", EnterPrice.getText().toString());
				Info.put("Description", EnterDescription.getText().toString());
				Info.put("Subject", EnterSubject.getText().toString());
				Info.put("Type", "Tutoring");
				Info.put("DateStamp", new SimpleDateFormat("yyyy-MM-dd").format(new java.sql.Date(new java.util.Date().getTime())));
				break;
			case 5:
				Info.put("Title", EnterTitle.getText().toString());
				Info.put("Price", EnterPrice.getText().toString());
				Info.put("Description", EnterDescription.getText().toString());
				Info.put("Type", "OddJob");
				Info.put("DateStamp", new SimpleDateFormat("yyyy-MM-dd").format(new java.sql.Date(new java.util.Date().getTime())));
				break;
			case 6:
				Info.put("Title", EnterTitle.getText().toString());
				Info.put("Price", EnterPrice.getText().toString());
				Info.put("Description", EnterDescription.getText().toString());
				Info.put("LocationOfOrigin", EnterLocationOfOrigin.getText().toString());
				Info.put("Destination", EnterDestination.getText().toString());
				Info.put("Date", EnterDate.getText().toString());
				Info.put("Type", "Carpooling");
				Info.put("DateStamp", new SimpleDateFormat("yyyy-MM-dd").format(new java.sql.Date(new java.util.Date().getTime())));
				break;
			}
			NetworkUtility digiNetwork = new NetworkUtility(NewDigiPostEnterInfoFragment.ParentActivity);
				digiNetwork.UploadNewDigiPost(Info, NewDigiPostEnterInfoFragment.this, ImagesArr);
				digiNetwork.execute();
				FullLayout.setVisibility(View.GONE);
				Loading.setVisibility(View.VISIBLE);
				AnimationDrawable d = (AnimationDrawable) Loading.getProgressDrawable();
				d.start();
		}
			else {
				Toast.makeText(getActivity(), "Missing Field(s)", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	private OnClickListener ImageClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			addImage = new AddImage((ImageButton) v);
		}
	};
	
	private TextWatcher mTextWatcher = new TextWatcher(){

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}
		@Override
		public void afterTextChanged(Editable s) {
			EnterPrice.removeTextChangedListener(mTextWatcher);
				StringBuilder sb = new StringBuilder();
				sb.insert(0, "$");
				sb.append(s.toString().trim().replaceAll("\\$", ""));
				EnterPrice.setText(sb.toString());
				EnterPrice.setSelection(EnterPrice.length());
			EnterPrice.addTextChangedListener(mTextWatcher);
		}
	};
	
	boolean FieldsFilled(){
		for (EditText Field : Fields){
			if(Field.getText().toString().trim().equals("")){
				return false;
			}
		}
		return true;
	}
	
	void Filler(int i){
		
	}

	@Override
	public void onTaskCompleted(HashMap<String, String> H) {
		System.out.println(H.get("Response"));
		System.out.println(H.get("Error"));
		System.out.println(H.get("Message"));
		if(Integer.parseInt(H.get("Error")) == 0){
			ParentActivity.startActivity(new Intent(ParentActivity, MainActivity.class));
			ParentActivity.finish();
		}
		else {
			FullLayout.setVisibility(View.VISIBLE);
			Loading.setVisibility(View.GONE);
			Toast.makeText(ParentActivity, H.get("Response"), Toast.LENGTH_SHORT).show();
		}
	}
	
	private class AddImage{
		
		private ImageButton Image;
		
		public AddImage(ImageButton b){
			Activity.startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
			Image = b;
		}
		
		public void onResult(String Path){
		//	UrlImageViewHelper.setUrlDrawable(Image, Path);
			BitmapHandler Handler = new BitmapHandler(Path, Image.getWidth(), Image.getHeight());
			Image.setScaleType(ScaleType.FIT_XY);
			Image.setImageBitmap(Handler.getBitmap());
			System.out.println("On Result2");
			switch(Image.getId()){
			case R.id.NewDigiPostIMG1:
				ImagesArr[0] = Path;
				break;
			case R.id.NewDigiPostIMG2:
				ImagesArr[1] = Path;
				break;
			case R.id.NewDigiPostIMG3:
				ImagesArr[2] = Path;
				break;
			case R.id.NewDigiPostIMG4:
				ImagesArr[3] = Path;
				break;
			case R.id.NewDigiPostIMG5:
				ImagesArr[4] = Path;
				break;
			case R.id.NewDigiPostIMG6:
				ImagesArr[5] = Path;
				break;
			}
		}
		
		
	}

	@Override
	public void onActivityResult(String Path) {
		System.out.println("OnResult");
		addImage.onResult(Path);
	}

	@Override
	public void onSort(int i) {
		// TODO Auto-generated method stub
		
	}
}

}
