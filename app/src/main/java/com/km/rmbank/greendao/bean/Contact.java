package com.km.rmbank.greendao.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by PengSong on 18/5/22.
 * 联系人、通讯录 实体类
 */

@Entity
public class Contact implements Parcelable {

    @Index
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "contact_id")
    private Long contactId;

    @Property(nameInDb = "contact_name")
    private String contactName;

    @Property(nameInDb = "contact_name_spell")
    private String contactNameSpell;

    @Property(nameInDb = "contact_name_first_letter")
    private String contactNameFirstLetter;

    @Property(nameInDb = "phone")
    private String phone;

    @Property(nameInDb = "is_invited")
    private boolean isInvited;

    @Transient
    private boolean checked;


    @Generated(hash = 1051199654)
    public Contact(Long id, Long contactId, String contactName, String contactNameSpell,
            String contactNameFirstLetter, String phone, boolean isInvited) {
        this.id = id;
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNameSpell = contactNameSpell;
        this.contactNameFirstLetter = contactNameFirstLetter;
        this.phone = phone;
        this.isInvited = isInvited;
    }


    @Generated(hash = 672515148)
    public Contact() {
    }


    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", contactId=" + contactId +
                ", contactName='" + contactName + '\'' +
                ", contactNameSpell='" + contactNameSpell + '\'' +
                ", phone='" + phone + '\'' +
                ", isInvited=" + isInvited +
                '}';
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Long getContactId() {
        return this.contactId;
    }


    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }


    public String getContactName() {
        return this.contactName;
    }


    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    public String getContactNameSpell() {
        return this.contactNameSpell;
    }


    public void setContactNameSpell(String contactNameSpell) {
        this.contactNameSpell = contactNameSpell;
    }


    public String getContactNameFirstLetter() {
        return this.contactNameFirstLetter;
    }


    public void setContactNameFirstLetter(String contactNameFirstLetter) {
        this.contactNameFirstLetter = contactNameFirstLetter;
    }


    public String getPhone() {
        return this.phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public boolean getIsInvited() {
        return this.isInvited;
    }


    public void setIsInvited(boolean isInvited) {
        this.isInvited = isInvited;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.contactId);
        dest.writeString(this.contactName);
        dest.writeString(this.contactNameSpell);
        dest.writeString(this.contactNameFirstLetter);
        dest.writeString(this.phone);
        dest.writeByte(this.isInvited ? (byte) 1 : (byte) 0);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected Contact(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.contactId = (Long) in.readValue(Long.class.getClassLoader());
        this.contactName = in.readString();
        this.contactNameSpell = in.readString();
        this.contactNameFirstLetter = in.readString();
        this.phone = in.readString();
        this.isInvited = in.readByte() != 0;
        this.checked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
