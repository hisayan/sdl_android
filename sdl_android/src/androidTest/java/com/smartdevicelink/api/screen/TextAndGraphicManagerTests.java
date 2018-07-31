package com.smartdevicelink.api.screen;

import android.content.Context;
import android.util.Log;

import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.api.FileManager;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.test.utl.AndroidToolsTests;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by brettywhite on 7/31/18.
 */
public class TextAndGraphicManagerTests extends AndroidToolsTests{

	// SETUP / HELPERS
	private Context mTestContext;
	private ISdl internalInterface;
	private FileManager fileManager;
	private TextAndGraphicManager textAndGraphicManager;

	@Override
	public void setUp() throws Exception{
		super.setUp();
		mTestContext = this.getContext();
		// mock things
		internalInterface = mock(ISdl.class);
		fileManager = mock(FileManager.class);

		textAndGraphicManager = new TextAndGraphicManager(internalInterface, fileManager, mTestContext);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInstantiation(){

		assertNull(textAndGraphicManager.getTextField1());
		assertNull(textAndGraphicManager.getTextField2());
		assertNull(textAndGraphicManager.getTextField3());
		assertNull(textAndGraphicManager.getTextField4());
		assertNull(textAndGraphicManager.getMediaTrackTextField());
		assertNull(textAndGraphicManager.getPrimaryGraphic());
		assertNull(textAndGraphicManager.getSecondaryGraphic());
		assertEquals(textAndGraphicManager.getTextAlignment(), TextAlignment.CENTERED);
		assertNull(textAndGraphicManager.getTextField1Type());
		assertNull(textAndGraphicManager.getTextField2Type());
		assertNull(textAndGraphicManager.getTextField3Type());
		assertNull(textAndGraphicManager.getTextField4Type());

		assertNotNull(textAndGraphicManager.currentScreenData);
		assertNull(textAndGraphicManager.inProgressUpdate);
		assertNull(textAndGraphicManager.queuedImageUpdate);
		assertEquals(textAndGraphicManager.hasQueuedUpdate, false);
		assertNull(textAndGraphicManager.displayCapabilities);
		assertEquals(textAndGraphicManager.currentHMILevel, HMILevel.HMI_NONE);
		assertEquals(textAndGraphicManager.isDirty, false);
		assertEquals(textAndGraphicManager.getState(), BaseSubManager.READY);
	}

	public void testGetMainLines(){

		// We want to test that the looping works. By default, it will return 4
		assertEquals(textAndGraphicManager.getNumberOfLines(), 4);

		// The tests.java class has an example of this, but we must build it to do what
		// we need it to do.

		TextField mainField1 = new TextField();
		mainField1.setName(TextFieldName.mainField1);
		TextField mainField2 = new TextField();
		mainField2.setName(TextFieldName.mainField2);
		TextField mainField3 = new TextField();
		mainField3.setName(TextFieldName.mainField3);
		TextField someOtherField = new TextField();
		someOtherField.setName(TextFieldName.menuName);

		List<TextField> textFieldList = new ArrayList<>();
		textFieldList.add(mainField1);
		textFieldList.add(mainField2);
		textFieldList.add(mainField3);
		textFieldList.add(someOtherField);

		DisplayCapabilities displayCapabilities = new DisplayCapabilities();
		displayCapabilities.setTextFields(textFieldList);

		textAndGraphicManager.displayCapabilities = displayCapabilities;

		assertEquals(textAndGraphicManager.getNumberOfLines(), 3);
	}
}
