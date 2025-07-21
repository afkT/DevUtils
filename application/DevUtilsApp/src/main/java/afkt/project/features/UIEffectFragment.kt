package afkt.project.features

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.app.buttonAdapter
import afkt.project.databinding.FragmentRecyclerViewBinding
import afkt.project.model.button.convertItemsUIEffectMain

class UIEffectFragment : AppFragment<FragmentRecyclerViewBinding, AppViewModel>(
    R.layout.fragment_recycler_view, BR.viewModel,
    simple_Agile = { frg -> frg.buttonAdapter()?.convertItemsUIEffectMain() }
)