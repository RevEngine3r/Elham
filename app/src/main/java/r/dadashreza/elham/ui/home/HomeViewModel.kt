package r.dadashreza.elham.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "سلام !\n" +
                "من الهام هستم !\n" +
                "من هر روز با هات حرف میزنم…\n" +
                "حتما حرفامو گوش بده، بنویس و عمل کن…"
    }
    val text: LiveData<String> = _text
}