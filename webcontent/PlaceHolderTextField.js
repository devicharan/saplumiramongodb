sap.ui.commons.TextField.extend("PlaceHolderTextField", {   
          metadata : {  
                    properties : {  
                              "placeholder" : "string"  
                    }  
          },  
          renderer : {  
                    renderInnerAttributes : function(oRm, oTextField) {  
                              oRm.writeAttributeEscaped('placeholder', oTextField.getPlaceholder());  
                              oRm.addStyle('background-color', '#fff'); // this change could also be done with plain CSS!!   
                    }  
          }  
});  