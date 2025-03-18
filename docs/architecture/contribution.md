# Contribution

Contributions are a way for different manifests to add specific bindings or settings needed by the target manifest. This means that one part of our system can offer certain capabilities or configurations that another part needs to work properly.

Most features and operations have their own contribution. In the following the most common ones are listed:

- Core
  - Diagram
    - `BGDiagramConfigurationContribution`
    - `BGDiagramEdgeConfigurationContribution`
    - `BGDiagramNodeConfigurationContribution`
  - Features
    - `BGDirectEditContribution`
    - `BGSuffixContribution`
    - `BGToolPaletteContribution`
  - GModel
    - `BGEMFGModelFeatureContribution`
  - Handler
    - `BGActionFeatureContribution`
    - `BGCreate|DeleteFeatureContribution`
    - `BGReconnectEdgeOperationContribution`
    - `BGOperationHandlerContribution`
- Elements
  - `BGConfigurationFeatureContribution`
- Features
  - `BGAutocompleteContribution`
  - `BGOutlineContribution`
  - `BGPropertyPaletteContribution`
