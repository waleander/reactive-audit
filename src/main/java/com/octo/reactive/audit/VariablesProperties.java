package com.octo.reactive.audit;

import java.util.Properties;

class VariablesProperties extends Properties
{


	// The prefix and suffix for constant names
	// within property values
	private static final String START_CONST = "${";
	private static final String END_CONST   = "}";


	private final Properties variables;

	public VariablesProperties(Properties variables)
	{
		this.variables = variables;
	}

	public String getProperty(String key)
	{


		String value = super.getProperty(key);
		if (value != null)
		{


			// Get the index of the first constant, if any
			int beginIndex = 0;
			int startName = value.indexOf(START_CONST, beginIndex);


			while (startName != -1)
			{

				int endName = value.indexOf(END_CONST, startName);
				if (endName == -1)
				{
					// Terminating symbol not found
					// Return the value as is
					return value;
				}


				String constName = value.substring(startName + START_CONST.length(), endName);
				String constValue = variables.getProperty(constName);


				if (constValue == null)
				{
					// Property name not found
					// Return the value as is
					return value;
				}


				// Insert the constant value into the
				// original property value
				String newValue = (startName > 0)
				                  ? value.substring(0, startName) : "";
				newValue += constValue;


				// Start checking for constants at this index
				beginIndex = newValue.length();


				// Append the remainder of the value
				newValue += value.substring(endName + 1);


				value = newValue;


				// Look for the next constant
				startName = value.indexOf(START_CONST, beginIndex);
			}
		}


		// Return the value as is
		return value;
	}
}
